package smarthome.thirdparty;
//third party library (adaptee in adapter pattern), cant be changed
public class OldRemote {
    public void turnOn(String device) {
        System.out.println("[OldRemote] " + device + " is now ON");
    }

    public void turnOff(String device) {
        System.out.println("[OldRemote] " + device + " is now OFF");
    }

    public void cmd(int id, String data) {
        System.out.println("[OldRemote]  cmd  " + id + ": " + data);
    }


}
