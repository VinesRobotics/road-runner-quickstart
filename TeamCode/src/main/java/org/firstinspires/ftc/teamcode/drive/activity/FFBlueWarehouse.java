package org.firstinspires.ftc.teamcode.drive.activity;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

/*
 * This is an example of a more complex path to really test the tuning.
 */
@Autonomous(name = "FFBlueWarehouse", group = "FF")
public class FFBlueWarehouse extends LinearOpMode {
    private double platformPower = 0.75;
    private double liftMotorPowerUp = -1;
    private double liftMotorPowerDown = 0.75;
    private static int TOP = -1100, UPPER_LEVEL = -724, MIDDLE_LEVEL = -217, LOWER_LEVEL = 248, BOTTOM = 589, STARTING_POS = -60;
    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        drive.setPoseEstimate(new Pose2d(-12, 65, Math.toRadians(270)));
        drive.liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        drive.liftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        // Trajectories
        Trajectory toShippingHub = drive.trajectoryBuilder(new Pose2d(-12, 65, Math.toRadians(270)), Math.toRadians(270))
                .splineToSplineHeading(new Pose2d(-12, 46, Math.toRadians(270)), Math.toRadians(270))
                .build();
        Trajectory toWarehouse = drive.trajectoryBuilder(new Pose2d(-12, 48, Math.toRadians(270)), Math.toRadians(270))
                .splineToSplineHeading(new Pose2d(-12, 55, Math.toRadians(270)), Math.toRadians(270))
                .splineToSplineHeading(new Pose2d(14, 45.10, Math.toRadians(0)), Math.toRadians(0))
                .build();

        waitForStart();

        if (isStopRequested()) return;


        // Movement
        while(!(drive.liftMotor.getCurrentPosition() <= (UPPER_LEVEL-70))){
            drive.liftMotor.setPower(liftMotorPowerUp);
        }
        drive.liftMotor.setPower(0.0);
        drive.followTrajectory(toShippingHub);
        while (drive.magnetSensor.getState()) {
            drive.platformMotor.setPower(platformPower);
        }
        drive.platformMotor.setPower(0.0);
        drive.intake.setPower(1.0);
        sleep(2000);
        drive.intake.setPower(0.0);
        drive.followTrajectory(toWarehouse);
        drive.cappingServo.setPosition(0.72);
        sleep(2000);
        while (!(drive.liftMotor.getCurrentPosition() >= (MIDDLE_LEVEL+70))) {
            drive.liftMotor.setPower(liftMotorPowerDown);
        }
    }
}
