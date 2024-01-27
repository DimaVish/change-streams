package org.dimawish.changestreams;

import org.dimawish.changestreams.repository.CarsMongoChangeStreamListener;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class ChangeStreamsApplicationTests {

	@MockBean
	CarsMongoChangeStreamListener carsMongoChangeStreamListener;
	@Test
	void contextLoads() {
	}

}
