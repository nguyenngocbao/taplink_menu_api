package taplink.network.menu.api.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resource, Long id) {
        super(resource + " could not be found for id: " + id);
    }

}
