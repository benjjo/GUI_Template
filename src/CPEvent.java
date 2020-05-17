import java.util.EventObject;

public class CPEvent extends EventObject{
    private String text;
    public CPEvent(Object source, String text){
        super(source);

        this.text = text;
    }

    public String getText() {
        return this.text;
    }
}