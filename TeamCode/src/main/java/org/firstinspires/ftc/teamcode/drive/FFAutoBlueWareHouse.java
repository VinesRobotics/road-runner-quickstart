/*
Copyright 2021 FIRST Tech Challenge Team 11341

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
associated documentation files (the "Software"), to deal in the Software without restriction,
including without limitation the rights to use, copy, modify, merge, publish, distribute,
sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial
portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.Blinker;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a PushBot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Remove a @Disabled the on the next line or two (if present) to add this opmode to the Driver Station OpMode list,
 * or add a @Disabled annotation to prevent this OpMode from being added to the Driver Station
 */
@Autonomous

public class FFAutoBlueWareHouse extends LinearOpMode {
    private Blinker control_Hub;
    private Blinker expansion_Hub_1;
    private DcMotor carouselMotor;
    private DigitalChannel touch;
    private Gyroscope imu2;
    private Gyroscope imu;
    private DcMotor leftBack;
    private DcMotor leftFront;
    //private CRServo markerServo;
    private DcMotor rightBack;
    private DcMotor rightFront;
    private CRServo intake;
    private DcMotor liftMotor;
    private DcMotor platformMotor;
    private Servo cappingServo;

    @Override
    public void runOpMode() {
        control_Hub = hardwareMap.get(Blinker.class, "Control Hub");
        expansion_Hub_1 = hardwareMap.get(Blinker.class, "Expansion Hub 1");
        carouselMotor = hardwareMap.get(DcMotor.class, "carouselMotor");
        touch = hardwareMap.get(DigitalChannel.class, "frontTouch");
        imu2 = hardwareMap.get(Gyroscope.class, "imu2");
        imu = hardwareMap.get(Gyroscope.class, "imu");
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        //markerServo = hardwareMap.get(CRServo.class, "markerServo");
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        liftMotor = hardwareMap.get(DcMotor.class, "liftMotor");
        intake = hardwareMap.get(CRServo.class, "intakeServo");
        platformMotor = hardwareMap.get(DcMotor.class, "platformMotor");
        cappingServo = hardwareMap.get(Servo.class, "cappingServo");

        double leftFrontPower = 0;
        double rightFrontPower = 0;
        double leftBackPower= 0;
        double rightBackPower= 0;
        double carouselMotorPower = 0;
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        //while (opModeIsActive()) {
        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        leftFront.setDirection(DcMotor.Direction.FORWARD);
        rightFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.FORWARD);
        rightBack.setDirection(DcMotor.Direction.REVERSE);
        telemetry.addData("Status", "Running");
        telemetry.update();

        // Forward and extend
        double power = 0.25;
        double turn  =  0.0;
        double strafe = 0.0;
        double drive = 0.25;
        leftFrontPower    = Range.clip(drive + turn + strafe, -power, power) ;
        leftBackPower = Range.clip(drive + turn - strafe, -power, power) ;
        rightFrontPower   = Range.clip(drive - turn - strafe, -power, power) ;
        rightBackPower = Range.clip(drive - turn + strafe, -power, power) ;
        leftFront.setPower(leftFrontPower);
        leftBack.setPower(leftBackPower);
        rightFront.setPower(rightFrontPower);
        rightBack.setPower(rightBackPower);
        platformMotor.setPower(1);
        telemetry.addData("Status", "Sleep");
        telemetry.update();
        sleep(1500);

        // brake
        power = 0;
        leftFrontPower    = power;
        leftBackPower = power;
        rightFrontPower   = power;
        rightBackPower = power;
        leftFront.setPower(leftFrontPower);
        leftBack.setPower(leftBackPower);
        rightFront.setPower(rightFrontPower);
        rightBack.setPower(rightBackPower);
        platformMotor.setPower(0.0);
        sleep(1000);

        // score
        intake.setPower(1.0);
        sleep(1000);

        // disengage
        power = 0.5;
        turn  =  0.0;
        strafe = 0.0;
        drive = -0.25;
        leftFrontPower    = Range.clip(drive + turn + strafe, -power, power) ;
        leftBackPower = Range.clip(drive + turn - strafe, -power, power) ;
        rightFrontPower   = Range.clip(drive - turn - strafe, -power, power) ;
        rightBackPower = Range.clip(drive - turn + strafe, -power, power) ;
        leftFront.setPower(leftFrontPower);
        leftBack.setPower(leftBackPower);
        rightFront.setPower(rightFrontPower);
        rightBack.setPower(rightBackPower);
        telemetry.addData("Status", "Sleep");
        telemetry.update();
        sleep(500);

        // stop
        power = 0;
        leftFrontPower    = power;
        leftBackPower = power;
        rightFrontPower   = power;
        rightBackPower = power;
        leftFront.setPower(leftFrontPower);
        leftBack.setPower(leftBackPower);
        rightFront.setPower(rightFrontPower);
        rightBack.setPower(rightBackPower);
        sleep(2000);

        // rotate and extend arm
        power = 0.5;
        turn  =  -0.5;
        strafe = 0.0;
        drive = 0.0;
        leftFrontPower    = Range.clip(drive + turn + strafe, -power, power) ;
        leftBackPower = Range.clip(drive + turn - strafe, -power, power) ;
        rightFrontPower   = Range.clip(drive - turn - strafe, -power, power) ;
        rightBackPower = Range.clip(drive - turn + strafe, -power, power) ;
        leftFront.setPower(leftFrontPower);
        leftBack.setPower(leftBackPower);
        rightFront.setPower(rightFrontPower);
        rightBack.setPower(rightBackPower);
        telemetry.addData("Status", "Sleep");
        telemetry.update();
        sleep(1500);

        // stop
        power = 0;
        leftFrontPower    = power;
        leftBackPower = power;
        rightFrontPower   = power;
        rightBackPower = power;
        leftFront.setPower(leftFrontPower);
        leftBack.setPower(leftBackPower);
        rightFront.setPower(rightFrontPower);
        rightBack.setPower(rightBackPower);
        sleep(2000);

        // forward
        power = 0.0;
        turn  =  0.0;
        strafe = 0.0;
        drive = 0.0;
        leftFrontPower    = Range.clip(drive + turn + strafe, -power, power) ;
        leftBackPower = Range.clip(drive + turn - strafe, -power, power) ;
        rightFrontPower   = Range.clip(drive - turn - strafe, -power, power) ;
        rightBackPower = Range.clip(drive - turn + strafe, -power, power) ;
        leftFront.setPower(leftFrontPower);
        leftBack.setPower(leftBackPower);
        rightFront.setPower(rightFrontPower);
        rightBack.setPower(rightBackPower);
        telemetry.addData("Status", "Sleep");
        telemetry.update();
        sleep(1500);

        // final stop
        power = 0;
        leftFrontPower    = power;
        leftBackPower = power;
        rightFrontPower   = power;
        rightBackPower = power;
        leftFront.setPower(leftFrontPower);
        leftBack.setPower(leftBackPower);
        rightFront.setPower(rightFrontPower);
        rightBack.setPower(rightBackPower);
        telemetry.addData("Status", "Complete");
        telemetry.update();
    }
}
