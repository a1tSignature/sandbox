package com.croc.sandbox;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;

import com.croc.sandbox.service.PostServiceImpl;
import com.croc.sandbox.service.TagServiceImpl;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

@SpringBootTest
@ActiveProfiles ("test")
@ComponentScan (basePackages = "com.croc.sandbox")
@Import({SandboxTestConfiguration.class})
class TransactionTests {

	@Autowired
	private PostServiceImpl postService;
	@Autowired
	private TagServiceImpl tagService;

	@BeforeEach
	public void beforeEachTest() {
		postService.newPost("test");
	}

	@AfterEach
	public void afterEachTest() {
		postService.deletePostByTitle("test");
	}

	@Test
	@DisplayName("Проверка на то, что readOnly транзакция убирает loaded состояние у сущности")
	public void testReadonlyTransaction() throws Exception {
		// Проверяем, что не было выброшено исключение. Ключевых кейсов для исключения всего два:
		// 1) Сущность не подтянулась в контекст
		// 2) У сущности, несмотря на readOnly транзакцию, появилось loaded состояние
		assertDoesNotThrow(() -> postService.findByTitle("test"));
		// Проверяем, что досталась нужная нам сущность
		assertEquals("Title", "test", postService.findByTitle("test").getTitle());

	}

	@Test
	@DisplayName("Проверка на то, что обычная транзакция поддерживает loaded состояние")
	public void testSimpleTransaction() throws Exception {
		UUID id = postService.findByTitle("test").getId();

		// Проверяем, что не было выброшено исключение. Ключевых кейсов для исключения всего один:
		// 1) У сущности отсутствует loaded состояние
		assertDoesNotThrow(() -> postService.findById(id));
		// Проверяем, что досталась нужная нам сущность
		assertEquals("Title", "test", postService.findByTitle("test").getTitle());

	}

	@Test
	@DisplayName("Проверка на то, что откат внутренней транзакции не влияет на внешнюю")
	public void testNestedTransaction() throws Exception {
		postService.newPostAndTag("post and tag");

		// Проверяем, что пост создался, несмотря на сфелившуюся внутреннюю транзакцию
		assertTrue("Existing post", postService.findByTitle("post and tag") != null);
		// Проверяем, что тэг откатился из-за сфейлившейся транзакции
		assertTrue("Existing tag", tagService.findByTitle("post and tag") == null);

	}

}
