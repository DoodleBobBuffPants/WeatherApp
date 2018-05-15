public class RequestFailed extends Exception {
    public RequestFailed(String msg) {
    	//custom exception if failed to contact API
        super(msg);
    }
}