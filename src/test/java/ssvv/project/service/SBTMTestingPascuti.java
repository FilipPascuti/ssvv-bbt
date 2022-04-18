package ssvv.project.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ssvv.project.domain.Nota;
import ssvv.project.domain.Student;
import ssvv.project.domain.Tema;
import ssvv.project.repository.NotaXMLRepository;
import ssvv.project.repository.StudentXMLRepository;
import ssvv.project.repository.TemaXMLRepository;
import ssvv.project.validation.NotaValidator;
import ssvv.project.validation.StudentValidator;
import ssvv.project.validation.TemaValidator;
import ssvv.project.validation.Validator;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.IntStream;

public class SBTMTestingPascuti {

    private StudentXMLRepository studentXMLRepository;
    private NotaXMLRepository notaXMLRepository;
    private TemaXMLRepository temaXMLRepository;

    private Service service;

    @Before
    public void setup() {
        Validator<Student> vs = new StudentValidator();
        Validator<Nota> ns = new NotaValidator();
        Validator<Tema> ts = new TemaValidator();

        studentXMLRepository = new StudentXMLRepository(vs, "src/test/java/resourses/studenti.xml");
        notaXMLRepository = new NotaXMLRepository(ns, "src/test/java/resourses/note.xml");
        temaXMLRepository = new TemaXMLRepository(ts, "src/test/java/resourses/teme.xml");
        service = new Service(studentXMLRepository, temaXMLRepository, notaXMLRepository);

    }

    @After
    public void tearDown() {
        try {
            String defaultFileContent = new String(Files.readAllBytes(Paths.get("src/test/java/resourses/teme_default.xml")), StandardCharsets.UTF_8);

            PrintWriter printWriter = new PrintWriter("src/test/java/resourses/teme.xml");

            printWriter.print(defaultFileContent);
            printWriter.close();


            defaultFileContent = new String(Files.readAllBytes(Paths.get("src/test/java/resourses/studenti_default.xml")), StandardCharsets.UTF_8);

            printWriter = new PrintWriter("src/test/java/resourses/studenti.xml");

            printWriter.print(defaultFileContent);
            printWriter.close();


            defaultFileContent = new String(Files.readAllBytes(Paths.get("src/test/java/resourses/note_default.xml")), StandardCharsets.UTF_8);

            printWriter = new PrintWriter("src/test/java/resourses/note.xml");

            printWriter.print(defaultFileContent);
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testWithoutStudent() {

        Assert.assertEquals(service.saveNota("0", "0", 7, 7, "mere"), -1);
    }

    @Test
    public void testWithValidInput() {

        service.saveStudent("0", "dorel", 933);
        service.saveTema("0", "tema1", 8, 5);
        Assert.assertEquals(service.saveNota("0", "0", 7, 7, "mere"), 1);
    }

    @Test
    public void testWithSubmitEarly() {

        service.saveStudent("0", "dorel", 933);
        service.saveTema("0", "tema1", 8, 5);
        Assert.assertEquals(service.saveNota("0", "0", 7, 4, "mere"), 1);
    }

    @Test
    public void testFeedbackEmpty() {

        service.saveStudent("0", "dorel", 933);
        service.saveTema("0", "tema1", 8, 5);
        Assert.assertEquals(service.saveNota("0", "0", 7, 8, ""), 1);
    }

    @Test
    public void testAddGradeTwice() {

        service.saveStudent("0", "dorel", 933);
        service.saveTema("0", "tema1", 8, 5);
        Assert.assertEquals(service.saveNota("0", "0", 7, 8, ""), 1);
        Assert.assertEquals(service.saveNota("0", "0", 7, 8, ""), 0);
    }

    @Test
    public void testWithSubmitLate() {
        service.saveStudent("0", "dorel", 933);
        service.saveTema("0", "tema1", 8, 5);
        Assert.assertEquals(service.saveNota("0", "0", 7, 20, ""), 1);
        service.findAllNote().forEach(System.out::println);
    }

    @Test
    public void testWithSuperLongFeedBackString() {
        service.saveStudent("0", "dorel", 933);
        service.saveTema("0", "tema1", 8, 5);
        Assert.assertEquals(service.saveNota("0", "0", 7, 8, "a".repeat(100000)), 1);
    }
}
