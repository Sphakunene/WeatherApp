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

    private Label currentLocationTitle;

    private Button showWeather;

    private Label currentTemp;

    public MainView(WeatherService weatherService) throws JSONException {
        this.weatherService = weatherService;
        setLayOut();
        setHeader();
        setLogo();
        setUpForm();
        dashBoardTitle();

    }

    private void dashBoardTitle() {
        HorizontalLayout dashBoardMain = new HorizontalLayout();
        Image image =new Image();
        image.setSrc("https://openweathermap.org/img/wn/01d@2x.png");
        dashBoardMain.setAlignItems(Alignment.CENTER);
        currentLocationTitle = new Label("Currently in Sandton");
        currentTemp = new Label("19F");
        currentTemp.getStyle().set("font-size", "45px");
        currentTemp.getStyle().set("font-weight", "bold");
        currentLocationTitle.getStyle().set("font-size", "25px");
        dashBoardMain.add(currentLocationTitle);
        dashBoardMain.add(image);
        dashBoardMain.add(currentTemp);
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
}


