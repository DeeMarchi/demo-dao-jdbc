package application;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.util.List;

public class DepartmentDaoTest {

    public static void testDepartmentDao() {

        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();

        System.out.println("=== TEST 1: department findById ===");
        Department department = departmentDao.findById(2);
        System.out.println(department);

        System.out.println("\n=== TEST 2: department findAll ===");
        List<Department> list = departmentDao.findAll();
        for (Department dep : list) {
            System.out.println(dep);
        }
    }
}
