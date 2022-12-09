package com.buildappwithcoleen.WeatherApp.View;

import com.buildappwithcoleen.WeatherApp.Controller.WeatherService;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import com.vaadin.flow.server.StreamResource;
import org.json.JSONArray;
import org.json.JSONException;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


@Route("")
public class MainView extends VerticalLayout {
    @Autowired
    private final WeatherService weatherService;
    private VerticalLayout mainLayout;
    private Select<String> unit;

    private TextField cityTextField;

    private Label currentLocationTitle;

    private Button showWeather;

    private Label currentTemp;

    private Label weatherDescription;
    private Label weatherMin;
    private Label weatherMax;
    private Label pressureLabel;
    private Label HumidityLabel;
    private Label windSpeedLabel;
    private Label sunRiseLabel;
    private Label sunSetLabel;
    private Image image;
    private HorizontalLayout dashBoardMain;
    private VerticalLayout descriptionLayout;
    private HorizontalLayout mainDescriptionLayout;
    private VerticalLayout pressureLayout;

    public MainView(WeatherService weatherService) {
        this.weatherService = weatherService;
        setLayOut();
        setHeader();
        setLogo();
        setUpForm();
        dashBoardTitle();
        dashBoardDescription();
        showWeather.addClickListener( buttonClickEvent -> {
            if(!cityTextField.getValue().equals("")){
                 updateUI();
            }else{
                Notification notification = Notification.show("please Enter the City!");
                mainLayout.add(notification);

            }

        });


    }



    private void dashBoardDescription() {
        mainDescriptionLayout = new HorizontalLayout();
        mainDescriptionLayout.setAlignItems(Alignment.CENTER);
        descriptionLayout = new VerticalLayout();
        descriptionLayout.setAlignItems(Alignment.AUTO);
        weatherDescription = new Label("Clear Skies");
        descriptionLayout.add(weatherDescription);

        weatherMin = new Label("Min : 56F");
        descriptionLayout.add(weatherMin);

        weatherMax = new Label("Clear Max : 86F");
        descriptionLayout.add(weatherMax);


        pressureLayout = new VerticalLayout();
        pressureLayout.setAlignItems(Alignment.AUTO);

        pressureLabel = new Label("Pressure : 123pa");
        pressureLayout.add(pressureLabel);

        HumidityLabel = new Label("Humidity : 34");
        pressureLayout.add(HumidityLabel);

        windSpeedLabel = new Label("Wind Speed: 124/hr");
        pressureLayout.add(windSpeedLabel);

        sunRiseLabel = new Label("Sunrise");
        pressureLayout.add(sunRiseLabel);

        sunSetLabel = new Label("Sunset");
        pressureLayout.add(sunSetLabel);
        
    }
    private void updateUI() {
        String city = cityTextField.getValue();
        String unit1;
        String defaultUnit;

        if (unit.getValue().equals("F")){
            defaultUnit = "imperial";
            unit1 = " "+"\u00b0"+ "F";
        }else{
            defaultUnit = "metric";
            unit1 =" "+ "\u00b0"+ "C";
        }
        System.out.println("selected value "+ unit.getValue());
        System.out.println(unit);
        weatherService.setUnit(defaultUnit);
        weatherService.setCityName(city);

        currentLocationTitle.setText("Currently in "+city);
        JSONObject jsonObject = weatherService.getMainObject();
        try {
            double temp = jsonObject.getDouble("temp") ;
            currentTemp.setText(temp+unit1);

            JSONObject mainObject = weatherService.getMainObject();
            double minTemp = mainObject.getDouble("temp_min");
            double maxTemp = mainObject.getDouble("temp_max");
            int pressure = mainObject.getInt("pressure");
            int humidity = mainObject.getInt("humidity");

            JSONObject windObject = weatherService.getWindObject();
            double wind = windObject.getDouble("speed");

            JSONObject systemObject = weatherService.getSunSetObject();
            long sunRise = systemObject.getLong("sunrise")*1000;
            long sunSet = systemObject.getLong("sunset")*1000;

            sunRiseLabel.setText("Sunrise : "+ convertTime(sunRise));
            sunSetLabel.setText("Sunset : "+ convertTime(sunSet));


            JSONArray jsonArray = weatherService.returnWeatherArray();
            String iconCode = null;
            String description="";
            for (int i = 0; i < jsonArray.length();i++){
                JSONObject jObj = jsonArray.getJSONObject(i);
                iconCode = jObj.getString("icon");
                description = jObj.getString("description");

            }

            weatherDescription.setText("Cloudiness: "+description);
            weatherMin.setText("Min :"+ String.valueOf(minTemp));
            weatherMax.setText("Max :"+ String.valueOf(maxTemp));
            pressureLabel.setText("Pressure :"+ String.valueOf(pressure)+" hpa");
            HumidityLabel.setText("Humidity :"+ String.valueOf(humidity)+" %");
            windSpeedLabel.setText("Wind : "+ String.valueOf(wind)+" m/s");


            image.setSrc("https://openweathermap.org/img/wn/"+iconCode+"@2x.png");
            mainDescriptionLayout.add(descriptionLayout);
            mainDescriptionLayout.add(pressureLayout);

            mainLayout.add(mainDescriptionLayout);
            dashBoardMain.add(currentLocationTitle,image,currentTemp);



        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }



    private void dashBoardTitle() {
         dashBoardMain = new HorizontalLayout();
         image =new Image();
        dashBoardMain.setAlignItems(Alignment.CENTER);
        currentLocationTitle = new Label("Currently in Sandton");
        currentTemp = new Label("19F");
        currentTemp.getStyle().set("font-size", "45px");
        currentTemp.getStyle().set("font-weight", "bold");
        currentLocationTitle.getStyle().set("font-size", "25px");
        mainLayout.add(dashBoardMain);

    }

    private void setUpForm() {
        HorizontalLayout formLayOut = new HorizontalLayout();
        formLayOut.setAlignItems(Alignment.CENTER);
        formLayOut.setMargin(true);
        formLayOut.setSpacing(true);
        unit = new Select<>();
        unit.setWidth("60px");
        ArrayList<String> items = new ArrayList<>();
        items.add("C");
        items.add("F");

        showWeather = new Button(new Icon(VaadinIcon.SEARCH));

        unit.setItems(items);
        unit.setValue(items.get(0));
        cityTextField = new TextField();
        cityTextField.setWidth("80%");
        formLayOut.add(unit);
        formLayOut.add(cityTextField);
        formLayOut.add(showWeather);
        mainLayout.add(formLayOut);


    }

    private void setLogo() {
        HorizontalLayout logoLayout = new HorizontalLayout();
        logoLayout.setAlignItems(Alignment.CENTER);
        StreamResource imageResource = new StreamResource("myimage.png",
                () -> getClass().getResourceAsStream("/rainy-day.png"));

        Image icon = new Image(imageResource, "My Streamed Image");
        icon.setWidth("123px");
        icon.setHeight("125px");
        logoLayout.add(icon);
        mainLayout.add(logoLayout);
    }

    private void setHeader() {
        HorizontalLayout hLayout = new HorizontalLayout();
        hLayout.setAlignItems(Alignment.CENTER);
        Label title = new Label("WEATHER!");
        title.getStyle().set("font-size", "35px");
        hLayout.add(title);


        mainLayout.add(hLayout);

    }

    public void setLayOut() {

        mainLayout = new VerticalLayout();
        mainLayout.setWidth("100%");
        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);
        mainLayout.setAlignItems(Alignment.CENTER);
        this.add(mainLayout);

    }

    private String convertTime(long time){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy hh.mm aa");
        return dateFormat.format(new Date(time));
    }
}


