package com.buildappwithcoleen.WeatherApp.View;

import com.buildappwithcoleen.WeatherApp.Controller.WeatherService;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import com.vaadin.flow.server.StreamResource;
import org.json.JSONException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

import java.util.ArrayList;


@Route("")
public class MainView extends VerticalLayout {
    @Autowired
    private WeatherService weatherService;
    private VerticalLayout mainLayout;
    private Select<String> unit;

    private TextField cityTextField;

    private Button showWeather;

    public MainView(WeatherService weatherService) throws JSONException {
        this.weatherService = weatherService;
        setLayOut();
        setHeader();
        setLogo();
        setUpForm();

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
        unit.setValue(items.get(0));
        showWeather = new Button(new Icon(VaadinIcon.SEARCH));

        unit.setItems(items);
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
        H1 title = new H1("WEATHER");

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
}


//    JSONArray jsonArray = weatherService.returnWeatherArray("sandton");
//    JSONObject obj = weatherService.getSunSetObject("sandton");
//        System.out.println("type"+obj.getInt("type"));
//
//                for (int i =0 ; i < jsonArray.length(); i++){
//        JSONObject jsonObject = jsonArray.getJSONObject(i);
//        System.out.println("id :"+jsonObject.getInt("id")+" main "+ jsonObject.getString("main")+
//        " description: "+ jsonObject.getString("description"));
//        }
//
//        Label label = new Label("Hello World");
//        add(label);
