import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

@Table("phone_manager.phone")
public class Phone extends Model {

    @Override
    public String toString() {
        return this.getString("mobile_producer") + " " + this.getString("mobile_model");
    }
}
