package org.firstinspires.ftc.teamcode.drive.opmode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.teamcode.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

import java.util.List;

/*
 * This is a straight line autonomous that will drop 1 wobble goal at square A on both sides of the field
 */

@Autonomous(group = "drive")
public class Auton_RedField extends LinearOpMode {
    //We have an issue with using the same auton for both sides. The start positions are different, and that could lead to potential issues.
    private double slowerVelocity = 8;
    String ringsDetected;
    SampleMecanumDrive drive;

    //TODO: Make an Interface to make these repeated portions of code inheritable
    //Initializing Trajectories
    Trajectory traj0;

    Trajectory trajA1, trajA2, trajA3, trajA4, trajA5, trajA6;

    Trajectory trajB1, trajB2, trajB3, trajB4, trajB5;

    Trajectory trajC1, trajC2, trajC3, trajC4, trajC5, trajC6, trajC7;

    @Override
    public void runOpMode() throws InterruptedException {

        //Init Hardware

        drive = new SampleMecanumDrive(hardwareMap);
        TFObjectDetector tfod = drive.getTfod();

        // Send telemetry to both the Drivers station and the Dashboard
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());


        if (tfod != null) {
            tfod.activate();

            // The TensorFlow software will scale the input images from the camera to a lower resolution.
            // This can result in lower detection accuracy at longer distances (> 55cm or 22").
            // If your target is at distance greater than 50 cm (20") you can adjust the magnification value
            // to artificially zoom in to the center of image.  For best results, the "aspectRatio" argument
            // should be set to the value of the images used to create the TensorFlow Object Detection model
            // (typically 16/9).
            tfod.setZoom(2.0, 16.0/9.0);
            telemetry.addLine("TFod Activated!");
            telemetry.update();
            tfod.setClippingMargins(0,0,0,0);
        }

        while (!opModeIsActive()) {
            if (tfod != null) {
                // getUpdatedRecognitions() will return null if no new information is available since
                // the last time that call was made.
                List<Recognition> recognitions = tfod.getRecognitions();
                if (recognitions != null) {
                    telemetry.addData("# Object Detected", recognitions.size());
                    // step through the list of recognitions and display boundary info.
                    int i = 0;
                    for (Recognition recognition : recognitions) {
                        ringsDetected = (String)recognition.getLabel();
                        telemetry.addData(String.format("label (%d)", i), (String)recognition.getLabel());
                        telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
                                recognition.getLeft(), recognition.getTop());
                        telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
                                recognition.getRight(), recognition.getBottom());
                    }
                    telemetry.update();
                    if (recognitions.size() == 0)
                        ringsDetected = "None";
//                    if(recognitions.size() != 0)
//                    {
//                        if (tfod != null)
//                            tfod.shutdown();
//                        break;
//                    }

                }
//                else {
//                    ringsDetected = "None";
//                }
            }
        }

        // Starting Position
        Pose2d startPose = new Pose2d(-63, -24, Math.toRadians(0));

        drive.setPoseEstimate(startPose);

if (ringsDetected == null)
    ringsDetected = "None";
        //Init trajectories
        //Shooting Position
        traj0 = drive.trajectoryBuilder(startPose)
                .lineToSplineHeading(new Pose2d(-17, -24, Math.toRadians(0)))
                .build();

switch (ringsDetected) {
    //Case C:
    case "Quad":

//        trajC1 = drive.trajectoryBuilder(traj0.end())
//                .lineTo(new Vector2d(56, -26))
//                .build();

        trajC2 = drive.trajectoryBuilder(traj0.end(), true)
                .lineToSplineHeading(new Pose2d(52, -56, Math.toRadians(-90)))
                .build();

        trajC3 = drive.trajectoryBuilder(trajC2.end())
                .lineToSplineHeading(new Pose2d(-25, -50, Math.toRadians(0)))
                .build();

        trajC4 = drive.trajectoryBuilder(trajC3.end())
                .lineTo(new Vector2d(-30, -50),
                        SampleMecanumDrive.getVelocityConstraint(10, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL)
                )
                .build();

        trajC5 = drive.trajectoryBuilder(trajC4.end())
                .lineToLinearHeading(new Pose2d(62, -42, Math.toRadians(90)))
                .build();

        trajC6 = drive.trajectoryBuilder(trajC5.end(), false)
                .strafeTo(new Vector2d(60, -36))
                .build();

        trajC7 = drive.trajectoryBuilder(trajC6.end(), false)
                .strafeTo(new Vector2d(18, -36))
                .build();

        break;

    //Case B:
    case "Single":

        trajB1 = drive.trajectoryBuilder(traj0.end())
//                .lineTo(new Vector2d(-24, 48))
                .lineTo(new Vector2d(36, -56))
                .build();

        trajB2 = drive.trajectoryBuilder(trajB1.end())
                .strafeTo(new Vector2d(-27, -56))
                .build();

        trajB3 = drive.trajectoryBuilder(trajB2.end())
                .lineTo(
                        new Vector2d(-29,-56),
                        SampleMecanumDrive.getVelocityConstraint(slowerVelocity, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL)
                )
                .build();

        trajB4 = drive.trajectoryBuilder(trajB3.end())
                .lineToSplineHeading(new Pose2d(20, -53, Math.toRadians(-135)))
                .build();

        trajB5 = drive.trajectoryBuilder(trajB4.end(), false)
                .forward(6)
                .build();
        break;

    //Case A:
    case "None":

//        trajA1 = drive.trajectoryBuilder(traj0.end())
//                .strafeTo(new Vector2d(0, -26))
//                .build();

        trajA2 = drive.trajectoryBuilder(traj0.end())
                .lineToSplineHeading(new Pose2d(0, -56, Math.toRadians(-90)))
                .build();

        trajA3 = drive.trajectoryBuilder(trajA2.end())
                .lineToSplineHeading(new Pose2d(-25, -52, Math.toRadians(0)))
                .build();

        trajA4 = drive.trajectoryBuilder(trajA3.end())
                .lineTo(new Vector2d(-30, -52),
                        SampleMecanumDrive.getVelocityConstraint(slowerVelocity, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL)
                )
                .build();

        trajA5 = drive.trajectoryBuilder(trajA4.end())
                .lineToSplineHeading(new Pose2d(16, -42, Math.toRadians(90)))
                .build();

        trajA6 = drive.trajectoryBuilder(trajA5.end())
                .forward(6)
                .build();

        break;

    default:
        break;
}
        waitForStart();
        if (isStopRequested()) return;
        //Use Tensorflo to figure out which path to use
        //Test all the paths.
        telemetry.clear();
        drive.wobbleArmServo.setPosition(drive.MAX_POS);
        switch(ringsDetected) {
            case "Quad": pathC(); break;
            case "Single": pathB(); break;
            case "None": pathA(); break;
            default: telemetry.addData("Default", "Default");
        }
        telemetry.update();


//        sleep(5000);

//        if (tfod != null)
//            tfod.shutdown();


//        wobbleDropper.setPosition(1);
//        drive.followTrajectory(
//                drive.trajectoryBuilder(traj.end(), true)
//                        .splineTo(new Vector2d(0, 0), Math.toRadians(180))
//                        .build()
//        );

    }

    private void pathA() {
        pathShoot();
//        drive.followTrajectory(trajA1);
        drive.followTrajectory(trajA2);
        drive.wobbleDrop();
        drive.moveTo("Down");
        sleep(1000);
        drive.followTrajectory(trajA3);
        drive.followTrajectory(trajA4);
        drive.wobbleGrab();
        sleep(1000);
        drive.moveTo("Carry");
        drive.followTrajectory(trajA5);
        drive.moveTo("Down");
        drive.wobbleRelease();
        sleep(1000);
        drive.followTrajectory(trajA6);
//        sleep(2000);
//        drive.moveTo("Away");
    }

    private void pathB() {
        pathShoot();
        drive.followTrajectory(trajB1);
        drive.wobbleDrop();
        drive.moveTo("Down");
        sleep(1000);
        drive.followTrajectory(trajB2);
        drive.followTrajectory(trajB3);
        drive.wobbleGrab();
        sleep(2000);
        drive.moveTo("Carry");
        drive.followTrajectory(trajB4);
        drive.moveTo("Down");
        drive.wobbleRelease();
        sleep(2000);
        drive.followTrajectory(trajB5);
    }

    private void pathC() {
        pathShoot();
//        drive.followTrajectory(trajC1);
        drive.followTrajectory(trajC2);
        drive.wobbleDrop();
        drive.moveTo("Down");
        sleep(750);
        drive.followTrajectory(trajC3);
        drive.followTrajectory(trajC4);
        drive.wobbleGrab();
        sleep(750);
        drive.moveTo("Carry");
        drive.followTrajectory(trajC5);
        drive.moveTo("Down");
        drive.wobbleRelease();
        sleep(750);
        drive.followTrajectory(trajC6);
        drive.followTrajectory(trajC7);
    }

    private void pathShoot() {
        drive.shooterMotor.setVelocity(drive.targetVel);
        drive.loaderPos = 0.0;
        drive.moveTo("Away");
        drive.followTrajectory(traj0);
        drive.shootRings(3);
    }

}
