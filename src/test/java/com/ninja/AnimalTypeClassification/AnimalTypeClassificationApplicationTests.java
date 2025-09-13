package com.ninja.AnimalTypeClassification;

import com.ninja.AnimalTypeClassification.services.AIService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class AnimalTypeClassificationApplicationTests {
    
    @Autowired
    private AIService aiService;

	@Test
	void contextLoads() {
	}
}
