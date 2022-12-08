package org.firstinspires.ftc.teamcode.drive.opmode.drive;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name="PowerPlayTeleOp", group="Mecanum Drive") //formerly MecanumWithSpeedToggle
@Config
public class PowerPlayTeleOp extends OpMode {
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor rightFront = null; //0 port, control hub
    private DcMotor leftFront = null; //1 port, control hub
    private DcMotor rightRear = null; //2 port, control hub
    private DcMotor leftRear = null; //3 port, control hub
    private DcMotor armElevation = null; // undefined port, expansion hub
    private Servo leftServo = null;
    private Servo rightServo = null;
    private double theta;
    private double stick_y, stick_x, Px, Py, right_stick_x;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        rightRear = hardwareMap.get(DcMotor.class, "rightRear");
        leftRear = hardwareMap.get(DcMotor.class, "leftRear");
        armElevation = hardwareMap.get(DcMotorEx.class, "armElevation");
        leftServo = hardwareMap.get(Servo.class, "leftServo");
        rightServo = hardwareMap.get(Servo.class, "rightServo");
        // Reverse the motor that runs backwards when connected directly to the battery
        rightFront.setDirection(DcMotorSimple.Direction.FORWARD);
        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightRear.setDirection(DcMotorSimple.Direction.FORWARD);
        leftRear.setDirection(DcMotorSimple.Direction.REVERSE);
        armElevation.setDirection(DcMotorSimple.Direction.FORWARD);
        armElevation.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        runtime.reset();
    }



    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        stick_x = -1*(gamepad1.left_stick_y * 1.0); // left to right movement modifier
        stick_y = gamepad1.left_stick_x * 1.0; // front to back movement modifier
        right_stick_x = gamepad1.right_stick_x * 0.5; // rotation movement modifier

        theta = (Math.atan2(stick_y, stick_x)) - (Math.PI / 2);
        Px = Math.sqrt(Math.pow(stick_x, 2) + Math.pow(stick_y, 2)) * (Math.sin(theta + Math.PI / 4));
        Py = Math.sqrt(Math.pow(stick_x, 2) + Math.pow(stick_y, 2)) * (Math.sin(theta - Math.PI / 4));

        rightFront.setPower(Px + right_stick_x);
        leftFront.setPower(Py - right_stick_x);
        rightRear.setPower(Py + right_stick_x);
        leftRear.setPower(Px - right_stick_x);

        if (gamepad1.a) {
            leftServo.setPosition(0.55);
            rightServo.setPosition(0.45);
        }

        if (gamepad1.b) {
            leftServo.setPosition(1.0);
            rightServo.setPosition(0.0);
        }

        if (gamepad1.dpad_up) {
            armElevation.setPower(0.7);
        } else if (gamepad1.dpad_down) {
            armElevation.setPower(-0.10);
        } else {
            armElevation.setPower(0.0);
        }
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop () {
    }

}
