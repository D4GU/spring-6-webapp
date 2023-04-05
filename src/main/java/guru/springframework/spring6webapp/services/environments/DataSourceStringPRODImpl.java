package guru.springframework.spring6webapp.services.environments;

import guru.springframework.spring6webapp.services.DataSourceString;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("PROD")
@Service("environmentService")
public class DataSourceStringPRODImpl implements DataSourceString {
    @Override
    public String printData() {
        return "PROD";
    }
}
