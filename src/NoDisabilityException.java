public class NoDisabilityException extends RuntimeException {

    public NoDisabilityException(String msg, Disability d) {
        super(msg + " � ������ � " + d.getName());
    }

}