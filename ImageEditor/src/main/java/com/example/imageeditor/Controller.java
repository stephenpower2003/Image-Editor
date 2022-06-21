package com.example.imageeditor;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.image.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private MenuItem menuExit, menuOpen;

    @FXML
    private ImageView mainIV, greyIV, redIV, blueIV, greenIV;

    @FXML
    private Label fileName, imageSize, greyH, greyB, greyS, redH, redB, redS, greenH, greenB, greenS, blueH, blueB, blueS;

    @FXML
    private Slider sliderHue, sliderSaturation, sliderBrightness;

    @FXML
    private Button resetImage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initSliders();
        setIV();
    }

    @FXML
    public void selectImage() {
        Window window = menuExit.getParentPopup().getOwnerWindow();
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(window);

        if (Utilities.validFile(file)) {
            Image image = new Image(file.toURI().toString(), mainIV.getFitWidth(), mainIV.getFitHeight(), true, true);

            mainIV.setImage(image);
            fileName.setText("File Name: " + file.getName());
            imageSize.setText("Image Size: " + image.getHeight() + " x " + image.getWidth());

            imageDetails();
            greyImage();
            colourChannels();
        }
    }

    @FXML
    public void resetImage() {
        sliderHue.setValue(0);
        sliderBrightness.setValue(0);
        sliderSaturation.setValue(0);
    }

    @FXML
    public void exitMenu() {
        System.exit(0);
    }

    public void greyImage() {
        Image image = mainIV.getImage();
        greyIV.setImage(image);
        ColorAdjust convert = new ColorAdjust();
        convert.setSaturation(-1);
        greyIV.setEffect(convert);
    }

    public void imageDetails() {
        ArrayList<Image> ivArray = new ArrayList<>();
        ivArray.add(greyIV.getImage());
        ivArray.add(redIV.getImage());
        ivArray.add(greenIV.getImage());
        ivArray.add(blueIV.getImage());

        for (Image image : ivArray) {
            ArrayList<Double> hueValues = new ArrayList<>();
            ArrayList<Double> brightnessValues = new ArrayList<>();
            ArrayList<Double> saturationValues = new ArrayList<>();

            if (Utilities.landscapeImage(image)) {
                for (int x = 1; x < image.getHeight(); x++) {
                    for (int y = 1; y < image.getWidth(); y++) {
                        Color color;
                        color = image.getPixelReader().getColor(y, x);
                        hueValues.add(color.getHue());
                        brightnessValues.add(color.getBrightness());
                        saturationValues.add(color.getSaturation());
                    }
                }
            }
            else if (Utilities.portraitImage(image) || Utilities.squareImage(image)){
                for (int x = 1; x < image.getWidth(); x++) {
                    for (int y = 1; y < image.getHeight(); y++) {
                        Color color;
                        color = image.getPixelReader().getColor(x, y);
                        hueValues.add(color.getHue());
                        brightnessValues.add(color.getBrightness());
                        saturationValues.add(color.getSaturation());
                    }
                }
            }

            Double totalHue = (double) 0;
            Double totalBrightness = (double) 0;
            Double totalSaturation = (double) 0;

            for (int x = 0; x < hueValues.size(); x++) {

                if (Utilities.validIndex(x, hueValues)) {
                    Double tempHue = hueValues.get(x);
                    totalHue = tempHue + totalHue;
                }

                if (Utilities.validIndex(x, brightnessValues)) {
                    Double tempBrightness = brightnessValues.get(x);
                    totalBrightness = tempBrightness + totalBrightness;
                }

                if (Utilities.validIndex(x, saturationValues)) {
                    Double tempSaturation = saturationValues.get(x);
                    totalSaturation = tempSaturation + totalSaturation;
                }
            }

            double averageHue = Utilities.division(totalHue, hueValues.size());
            double averageBrightness = Utilities.division(totalBrightness, brightnessValues.size());
            double averageSaturation = Utilities.division(totalSaturation, saturationValues.size());

            if (image == greyIV.getImage()) {
                greyH.setText("Hue = " + Utilities.decimalFormat(averageHue));
                greyB.setText("Brightness = " + Utilities.decimalFormat(averageBrightness));
                greyS.setText("Saturation = " + Utilities.decimalFormat(averageSaturation));
            }

            if (image == redIV.getImage()) {
                redH.setText("Hue = " + Utilities.decimalFormat(averageHue));
                redB.setText("Brightness = " + Utilities.decimalFormat(averageBrightness));
                redS.setText("Saturation = " + Utilities.decimalFormat(averageSaturation));
            }

            if (image == greenIV.getImage()) {
                greenH.setText("Hue = " + Utilities.decimalFormat(averageHue));
                greenB.setText("Brightness = " + Utilities.decimalFormat(averageBrightness));
                greenS.setText("Saturation = " + Utilities.decimalFormat(averageSaturation));
            }

            if (image == blueIV.getImage()) {
                blueH.setText("Hue = " + Utilities.decimalFormat(averageHue));
                blueB.setText("Brightness = " + Utilities.decimalFormat(averageBrightness));
                blueS.setText("Saturation = " + Utilities.decimalFormat(averageSaturation));
            }
        }
    }

    public void colourChannels() {
        Image image = mainIV.getImage();
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();

        WritableImage redImage = new WritableImage(width, height);
        WritableImage greenImage = new WritableImage(width, height);
        WritableImage blueImage = new WritableImage(width, height);

        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                Color c = image.getPixelReader().getColor(y, x);
                redImage.getPixelWriter().setColor(y, x, new Color(c.getRed(), 0, 0, 1.0));
                greenImage.getPixelWriter().setColor(y, x, new Color(0, c.getGreen(), 0, 1.0));
                blueImage.getPixelWriter().setColor(y, x, new Color(0, 0, c.getBlue(), 1.0));
            }
        }
        redIV.setImage(redImage);
        greenIV.setImage(greenImage);
        blueIV.setImage(blueImage);
    }

    public void updateHue() {
        ColorAdjust hueAdjust = new ColorAdjust();
        hueAdjust.setHue(sliderHue.getValue());
        mainIV.setEffect(hueAdjust);
    }

    public void updateSaturation() {
        ColorAdjust saturationAdjust = new ColorAdjust();
        saturationAdjust.setSaturation(sliderSaturation.getValue());
        mainIV.setEffect(saturationAdjust);
    }

    public void updateBrightness() {
        ColorAdjust brightnessAdjust = new ColorAdjust();
        brightnessAdjust.setBrightness(sliderBrightness.getValue());
        mainIV.setEffect(brightnessAdjust);
    }

    ChangeListener<Number> sliderChangeHue
            = new ChangeListener<Number>(){

        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            updateHue();
        }
    };

    ChangeListener<Number> sliderChangeSaturation
            = new ChangeListener<Number>(){

        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            updateSaturation();
        }
    };

    ChangeListener<Number> sliderChangeBrightness
            = new ChangeListener<Number>(){

        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            updateBrightness();
        }
    };

    private void setIV () {
        File file = new File("src/main/resources/com/example/imageeditor/misouri_image.jpg");
        Image image = new Image(file.toURI().toString());

        mainIV.setImage(image);
        fileName.setText("File Name: " + file.getName());
        imageSize.setText("Image Size: " + image.getHeight() + " x " + image.getWidth());

        greyImage();
        colourChannels();
        imageDetails();
    }

    private void initSliders() {
        sliderHue.valueProperty().addListener(sliderChangeHue);
        sliderSaturation.valueProperty().addListener(sliderChangeSaturation);
        sliderBrightness.valueProperty().addListener(sliderChangeBrightness);

        sliderHue.setMin(-360);
        sliderHue.setMax(360);
        sliderHue.setBlockIncrement(30);

        sliderBrightness.setMin(-1);
        sliderBrightness.setMax(1);
        sliderBrightness.setBlockIncrement(0.2);

        sliderSaturation.setMin(-1);
        sliderSaturation.setMax(1);
        sliderSaturation.setBlockIncrement(0.2);
    }

}