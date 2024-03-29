package njgis.opengms.portal.AbstractTask;

import com.alibaba.fastjson.JSONObject;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

@Component
public class AsyncTask extends AbstractTask {

    @Async
    public Future<String> getRecordCallback(JSONObject param) throws  Exception{
        JSONObject result=super.getRecord(param);
        return new AsyncResult<>(result.toJSONString());
    }
}
