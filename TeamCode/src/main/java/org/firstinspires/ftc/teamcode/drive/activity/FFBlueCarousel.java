package org.firstinspires.ftc.teamcode.drive.activity;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.teamcode.drive.activity.FFTensorDetect;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

import java.util.Arrays;

/*
 * FINISHED AUTONOMOUS
 */
@Autonomous(name = "FFBlueCarousel", group = "FF",preselectTeleOp = "FFTeleOp")
public class FFBlueCarousel extends LinearOpMode {
    private double carouselPowerForward = 0.60;
    private double carouselPowerBackwards = -0.60;
    private double liftMotorPowerUp = -1;
    private double liftMotorPowerDown = 0.75;
    private static int UPPER_LEVEL = -724;
    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        FFTensorDetect tensorDetect = new FFTensorDetect();
        drive.setPoseEstimate(new Pose2d(-28.4, 65.0, Math.toRadians(270.0)));
        drive.liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        drive.liftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        tensorDetect.initVuforia();
        tensorDetect.initTfod();



        /*
          Activate TensorFlow Object Detection before we wait for the start command.
          Do it here so that the Camera Stream window will have the TensorFlow annotations visible.
         */
        if (tensorDetect.tfod != null) {
            tensorDetect.tfod.activate();
            tensorDetect.tfod.setZoom(2.0, 16.0/9.0);
//            tfod.setClippingMargins(216,
//                    384,
//                    216,
//                    384);
        }

        /* Wait for the game to begin */
        telemetry.addData(">", "Press Play to start op mode");
        telemetry.update();
        while (!isStarted()) {
            double[] rawSideVal = tensorDetect.detectObjectsStart();
            if (!(rawSideVal == null)) {
                if (!(Arrays.equals(rawSideVal, new double[]{0.0, 0.0}))) {
                    if (rawSideVal[0] >= 0.75) {
                        tensorDetect.side = 1;
                    } else if (rawSideVal[1] >= 0.75) {
                        tensorDetect.side = 2;
                    }
                }
            }
        }
        switch (tensorDetect.side) {
            case 0: telemetry.addData("No side: ", tensorDetect.side); break;
            case 1: telemetry.addData("Left side: ", tensorDetect.side); break;
            case 2: telemetry.addData("Right side: ", tensorDetect.side); break;
        }
        telemetry.update();

        if (isStopRequested()) return;

        Trajectory toCarousel = drive.trajectoryBuilder(new Pose2d(-28.75, 62.4, Math.toRadians(270.0)))
                .splineToSplineHeading(new Pose2d(-36, 36, Math.toRadians(175)), Math.toRadians(150))
                .splineToSplineHeading(new Pose2d(-60,58, Math.toRadians(74)),Math.toRadians(77))
                .build();
        Trajectory toStorage = drive.trajectoryBuilder(new Pose2d(-60,56, Math.toRadians(74)),Math.toRadians(77))
                .splineToSplineHeading(new Pose2d(-60, 38, Math.toRadians(100)), Math.toRadians(90))
                .build();
        drive.followTrajectory(toCarousel);
        sleep(250);
        drive.carouselMotor.setPower(carouselPowerBackwards);
        sleep(3000);
        drive.carouselMotor.setPower(0.0);
        drive.followTrajectory(toStorage);
        while(!(drive.liftMotor.getCurrentPosition() <= (UPPER_LEVEL-70))){
            drive.liftMotor.setPower(liftMotorPowerUp);
        }
        drive.liftMotor.setPower(0.0);
        drive.cappingServo.setPosition(0.34);
    }
}
