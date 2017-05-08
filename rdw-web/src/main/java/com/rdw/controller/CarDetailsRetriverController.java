package com.rdw.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rdw.domain.CarData;
import com.rdw.service.CarDataService;
import nl.webservices.soap.WebservicesNlPortType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Created by x073880 on 3/29/2017.
 */

@RestController
public class CarDetailsRetriverController {

    @Autowired
    private CarDataService carDataService;

    @Autowired
    private WebservicesNlPortType carRDWCarData;


    @RequestMapping("/")
    public String getCarDetails() {

        String fileName = "kentekens.txt";
        Map<String, CarData> carDatas = new HashMap<>();

        //read file into stream, try-with-resources
        try (Stream<String> stream = Files.lines(Paths.get(ClassLoader.getSystemResource(fileName).toURI()))) {
            stream.forEach(item -> {
                System.out.println(item);
                CarData car = carDatas.get(item);
                if (carDatas.get(item) == null) {
                    CarData carData = carDataService.getCarDatas(item);
                    carDatas.put(item, carData);
                }
            });
        } catch (IOException |  URISyntaxException e) {
            e.printStackTrace();
        }
        System.out.format("%2s%16s%16s%16s%16s%16s%16s", "License Plate", "Brand", "Model","Color","FuelType","apkDueDate","ApkExpired","");
        System.out.println();
        carDatas.values().forEach(item -> {
            System.out.format("%2s%16s%16s%16s%16s%16s%16s", item.getLicenseplate(), item.getBrand(), item.getModel(),item.getColor(),item.getFuelType(),item.getApkDueDate(),item.isAPKExpired(),"");
            System.out.println();
        });
        Gson objGson = new GsonBuilder().setPrettyPrinting().create();
        String jsonResponse = objGson.toJson(carDatas.values());
        return jsonResponse;
    }




}
