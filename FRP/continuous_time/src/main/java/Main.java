import nz.sodium.Cell;
import nz.sodium.StreamSink;
import nz.sodium.Unit;
import nz.sodium.time.SecondsTimerSystem;
import nz.sodium.time.TimerSystem;
import swidgets.SLabel;

import java.awt.Label;

import javax.swing.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        setUpLogic();
        loop();
    }

    private static void setUpLogic() {
        JFrame frame = new JFrame("Continuous Time 2");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        TimerSystem timerSystem = new SecondsTimerSystem();
        Cell<Double> time = timerSystem.time;

        SLabel slTime = new SLabel(time.map(t -> "Time: "+t));
        frame.add(slTime);
        
        SLabel slSpeed = new SLabel(time.map(t -> "Speed: "+t*9.81));
        frame.add(slSpeed);
        
        SLabel slDistance = new SLabel(time.map(t -> "Distance: "+ 9.81/2*Math.pow(t, 2)));
        frame.add(slDistance);

        frame.setSize(400, 160);
        frame.setVisible(true);
    }

    private static void loop() throws InterruptedException {
        long systemSampleRate = 1000L;
        StreamSink<Unit> sMain = new StreamSink<>();
        while(true) {
            //jede Transaktion aktualisiert TimerSystem.time (Sodium spezifisch) -> send löst Transaktion aus
            sMain.send(Unit.UNIT);
            Thread.sleep(systemSampleRate);
        }
    }
}
