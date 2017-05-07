package com.rdw.service;

import com.rdw.domain.CarData;
import nl.webservices.soap.CarRDWCarDataRequestType;
import nl.webservices.soap.CarRDWCarDataResponseType;
import nl.webservices.soap.WebservicesNlPortType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * CarData Service is the class to connect to with the Webservice Nl CarData Service
 * Created by x073880 on 5/7/2017.
 */
@Service
public class CarDataService {

    @Autowired
    private WebservicesNlPortType carRDWdata;

    /**
     * Method to get all car datas with the help of the license plate number
     *
     * @param licensePlate of type String holds the License number of the car to be searched
     * @return CarData {@link CarData}
     */
    public CarData getCarDatas(String licensePlate) {

        CarRDWCarDataRequestType carRDWCarDataRequest = null;
        CarRDWCarDataResponseType carRDWCarDataResponse = null;
        CarData carData = null;
        try {
            carRDWCarDataRequest = new CarRDWCarDataRequestType();
            carRDWCarDataRequest.setLicensePlate(licensePlate);
            carRDWCarDataResponse = carRDWdata.carRDWCarData(carRDWCarDataRequest);
        } catch (Exception e) {
            System.out.println("Exception in fetching car details of" + licensePlate);
        }
        if (carRDWCarDataResponse != null) {
            carData = createCarData(carRDWCarDataResponse);
        } else {
            carData = new CarData();
            carData.setLicenseplate(licensePlate);
        }
        return carData;
    }

    /**
     * Method to convert the CarRDWDataResponseType to local domain CarData
     *
     * @param carRDWCarDataResponse of type CarRDWCarDataResponse {@link CarRDWCarDataResponseType}
     * @return CarDate {@link CarData}
     */
    private CarData createCarData(CarRDWCarDataResponseType carRDWCarDataResponse) {
        CarData carData = new CarData();
        carData.setLicenseplate(carRDWCarDataResponse.getOut().getLicensePlate());
        carData.setApkDueDate(carRDWCarDataResponse.getOut().getApkDueDate());
        carData.setBrand(carRDWCarDataResponse.getOut().getBrand());
        carData.setColor(carRDWCarDataResponse.getOut().getColors());
        carData.setModel(carRDWCarDataResponse.getOut().getModel());
        carData.setFuelType(carRDWCarDataResponse.getOut().getFuelType());
        getCarAPKExpiry(carRDWCarDataResponse, carData);
        return carData;
    }

    /**
     * Method to check the Car apk expiry date is already expired or not
     * @param carRDWCarDataResponse of type carRDWCarDataResponse {@link CarRDWCarDataResponseType}
     * @param carData of type CarData {@link CarData}
     */
    private void getCarAPKExpiry(CarRDWCarDataResponseType carRDWCarDataResponse, CarData carData) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            LocalDate apkDate = LocalDate.parse(carRDWCarDataResponse.getOut().getApkDueDate(), formatter);
            LocalDate today = LocalDate.now();
            boolean isBeforeToday = apkDate.isBefore(today);
            carData.setAPKExpired(isBeforeToday);
        } catch (DateTimeException e) {
            System.out.print("Failed while parsing a date of the car data" + carRDWCarDataResponse.getOut().getLicensePlate() + carRDWCarDataResponse.getOut().getApkDueDate());
        }
    }


}
