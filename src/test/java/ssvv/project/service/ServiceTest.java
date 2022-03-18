package ssvv.project.service;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ssvv.project.domain.Nota;
import ssvv.project.domain.Student;
import ssvv.project.domain.Tema;
import ssvv.project.repository.*;
import ssvv.project.validation.NotaValidator;
import ssvv.project.validation.StudentValidator;
import ssvv.project.validation.TemaValidator;
import ssvv.project.validation.Validator;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ServiceTest {

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
            String defaultFileContent = new String(Files.readAllBytes(Paths.get("src/test/java/resourses/studenti_default.xml")), StandardCharsets.UTF_8);

            PrintWriter printWriter = new PrintWriter("src/test/java/resourses/studenti.xml");

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
    public void TC1_BBT_EC() {
        service.findAllStudents().forEach(System.out::println);
        var result = service.saveStudent("0", "Dorel", 111);
        service.findAllStudents().forEach(System.out::println);
        Assert.assertEquals(result, 1);
    }

    @Test
    public void TC2_BBT_EC() {
        Assert.assertEquals(service.saveStudent(null, "Dorel", 937), 0);
    }

    @Test
    public void TC3_BBT_EC() {
        Assert.assertEquals(service.saveStudent("", "Dorel", 911), 0);
    }

    @Test
    public void TC4_BBT_EC() {
        Assert.assertEquals(service.saveStudent("0", null, 911), 0);
    }

    @Test
    public void TC5_BBT_EC() {
        Assert.assertEquals(service.saveStudent("0", "", 911), 0);
    }

    @Test
    public void TC6_BBT_EC() {
        Assert.assertEquals(service.saveStudent("0", "Dorel", 110), 0);
    }

    @Test
    public void TC7_BBT_EC() {
        Assert.assertEquals(service.saveStudent("0", "Dorel", 938), 0);
    }

    @Test
    public void TC1_BBT_BVA() {
        Assert.assertEquals(service.saveStudent("0", "Dorel", 110), 0);
    }



}
