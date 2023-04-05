package guru.springframework.spring6webapp.services.environments;

import guru.springframework.spring6webapp.services.DataSourceString;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
@Profile("UAT")
@Service("environmentService")
public class DataSourceStringUATImpl implements DataSourceString {
    @Override
    public String printData() {
        return "UAT";
    }
}
