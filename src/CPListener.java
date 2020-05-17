import java.util.EventListener;

public interface CPListener extends EventListener {
    void detailEventOccurred(CPEvent event);
}