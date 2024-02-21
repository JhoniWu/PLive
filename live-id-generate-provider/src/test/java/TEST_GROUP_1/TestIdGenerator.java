package TEST_GROUP_1;

import com.prayer.live.id.generate.IdGenerateApplication;
import com.prayer.live.id.generate.service.IdGenerateService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-02-07 14:51
 **/
@SpringBootTest(classes = IdGenerateApplication.class)
@RunWith(SpringRunner.class)
public class TestIdGenerator {
	@Autowired
	IdGenerateService idGenerateService;
	@Test
	public void testIdGenerate(){
		for(int i = 0; i < 70; i++){
			Thread th = new Thread(()->{
				Long seqId = idGenerateService.getSeqId(2);
				System.out.println(seqId);
			});
			th.start();
		}
	}
}
