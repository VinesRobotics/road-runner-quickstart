package org.firstinspires.ftc.teamcode.drive.activity;

import android.annotation.SuppressLint;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.util.Arrays;
import java.util.List;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

/**
 * This 2020-2021 OpMode illustrates the basics of using the TensorFlow Object Detection API to
 * determine the position of the Freight Frenzy game elements.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list.
 *
 * IMPORTANT: In order to use this OpMode, you need to obtain your own Vuforia license key as
 * is explained below.
 */
@Autonomous(name = "FFTensorDetect", group = "FF")

public class FFTensorDetect extends LinearOpMode {
    public int side = 0;

    public static final String TFOD_MODEL_ASSET = "FreightFrenzy_BCDM.tflite";
    public static final String[] LABELS = {
            "Ball",
            "Cube",
            "Duck",
            "Marker"
    };

    public static final String VUFORIA_KEY =
            "AYyrYgn/////AAABmfoVAabL9068k4+3G7BnkUsn3CiCAh0CExHOl7JMywbgqm+E6EGnvUJu4GmpbxVoJT9fzwRQVJtOg0/0UM8utTn6W+zUBcMxW/pVpPa5yaL3dhcgq8z58hV0f1dbDYJxcBlikT49wSdyoZlG/Cs3HSpplIuK/3xAqBUUYjqFfQ754AYtY2eCz2HWjnAB9IHcHE3MX9TRmEsJMGI6ZMkbMp7N5kxlZDX3yL+36sekpn3NdYfuvDPrQD7DEbGYEYHg8FERjE6vpx1OhU6sLAfUVN3o40Qrct/G3pKndmMJ1MnvAVXPE2llAmXvdQwTNMYRq/BXj446ICd3eCCXj9kozasAra6fJRkn9jFk1FOE4OVS";

    /**
     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    public VuforiaLocalizer vuforia;

    /**
     * {@link #tfod} is the variable we will use to store our instance of the TensorFlow Object
     * Detection engine.
     */
    public TFObjectDetector tfod;

    @Override
    public void runOpMode() {
        // The TFObjectDetector uses the camera frames from the VuforiaLocalizer, so we create that
        // first.
        initVuforia();
        initTfod();



        /*
          Activate TensorFlow Object Detection before we wait for the start command.
          Do it here so that the Camera Stream window will have the TensorFlow annotations visible.
         */
        if (tfod != null) {
            tfod.activate();
            tfod.setZoom(2.0, 16.0/9.0);
//            tfod.setClippingMargins(216,
//                    384,
//                    216,
//                    384);
        }

        /* Wait for the game to begin */
        telemetry.addData(">", "Press Play to start op mode");
        telemetry.update();
        while (!isStarted()) {
            double[] rawSideVal = detectObjectsStart();
//            double[] rawSideVal = new double[] {0.79, 0.5};
            if (!(rawSideVal == null)) { // MAKE OBJCT DETECT BETTER
                if (!(Arrays.equals(rawSideVal, new double[]{0.0, 0.0}))) {
                    if (rawSideVal[0] >= 0.75) {
                        side = 1;
                    } else if (rawSideVal[1] >= 0.75) {
                        side = 2;
                    }
                }
            }
        }
        switch (side) {
            case 0: telemetry.addData("No/Left side: ", side); break;
            case 1: telemetry.addData("Middle side: ", side); break;
            case 2: telemetry.addData("Right side: ", side); break;
        }
        telemetry.update();




    }

    /**
     * Initialize the Vuforia localization engine.
     */
    public void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the TensorFlow Object Detection engine.
    }

    /**
     * Initialize the TensorFlow Object Detection engine.
     */
    public void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = 0.8f;
        tfodParameters.isModelTensorFlow2 = true;
        tfodParameters.inputSize = 320;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABELS);
    }

    @SuppressLint("DefaultLocale")
    public double[] detectObjectsStart() {
        if (tfod != null) {
            // getUpdatedRecognitions() will return null if no new information is available since
            // the last time that call was made.
            List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
            if (updatedRecognitions != null) {
                telemetry.addData("# Object Detected", updatedRecognitions.size());
                // step through the list of recognitions and display boundary info.
                int i = 0;
                for (Recognition recognition : updatedRecognitions) {
                    telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
                    telemetry.addData(String.format("confidence (%d)", i), "%.03f", recognition.getConfidence());
                    telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
                            recognition.getLeft(), recognition.getTop());
                    telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
                            recognition.getRight(), recognition.getBottom());
                    i++;
                    if (recognition.getLabel().equalsIgnoreCase("duck")) { // if the confidence for a "duck" is higher
                        // than confidence c then return the value and break the loop
                        return new double[]{recognition.getLeft(), recognition.getRight()};
                    }
                }
                telemetry.update();
            }
        }
        return new double[]{0.0, 0.0};
    }
}
