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

public class ServiceTestWWT {

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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void TestAddStudent() {
        assert true;
    }


    @Test
    public void TC_WWT_1() {
        Assert.assertEquals(service.saveTema(null, "asdf", 5, 3), 1);
    }

    @Test
    public void TC_WWT_2() {
        Assert.assertEquals(service.saveTema("0", null, 5, 3), 1);
    }

    @Test
    public void TC_WWT_3() {
        Assert.assertEquals(service.saveTema("0", "asdf", 0, 3), 1);
    }

    @Test
    public void TC_WWT_4() {
        Assert.assertEquals(service.saveTema("0", "asdf", 5, 15), 1);
    }

    @Test
    public void TC_WWT_5() {
        Assert.assertEquals(service.saveTema("0", "asdf", 5, 3), 0);
    }


}
