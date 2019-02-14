public class NoDisabilityException extends RuntimeException {

    public NoDisabilityException(String msg, Disability d) {
        super(msg + " А именно в " + d.getName());
    }

}