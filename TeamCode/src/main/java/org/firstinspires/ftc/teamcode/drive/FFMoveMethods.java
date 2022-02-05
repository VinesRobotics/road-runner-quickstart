package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
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

public abstract class FFMoveMethods extends OpMode {
    private Blinker control_Hub;
    private Blinker expansion_Hub_1;
    private DcMotor carouselMotor;
    private DigitalChannel touch;
    private Gyroscope imu2;
    private Gyroscope imu;
    private DcMotor leftBack;
    private DcMotor leftFront;
    private DcMotor rightBack;
    private DcMotor rightFront;
    private CRServo intake;
    private DcMotor liftMotor;
    private DcMotor platformMotor;
    private Servo cappingServo;

        public void initEncoders() {
            carouselMotor = hardwareMap.get(DcMotor.class, "carouselMotor");
            liftMotor = hardwareMap.get(DcMotor.class, "liftMotor");
            platformMotor = hardwareMap.get(DcMotor.class, "platformMotor");
            platformMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            carouselMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }
        public void moveCarousel(double power, boolean direction) {
            carouselMotor = hardwareMap.get(DcMotor.class, "carouselMotor");
            carouselMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            if (direction) {
                carouselMotor.setPower(power);
            } else {
                carouselMotor.setPower(-power);
            }
        }
        public void moveLiftAuto(int level) {
        }
        public void platformMotor(long dist, boolean direction) {
            platformMotor = hardwareMap.get(DcMotor.class, "platformMotor");
            platformMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            if (direction) {
                platformMotor.setPower(-1.0);
            } else {
                platformMotor.setPower(0.75);
            }
            platformMotor.setPower(0.0);
        }
    }
