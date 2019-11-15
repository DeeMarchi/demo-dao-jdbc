package model.dao.implementation;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DepartmentDaoJDBC implements DepartmentDao {

    private Connection conn;

    public DepartmentDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Department obj) {

    }

    @Override
    public void update(Department obj) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Department findById(Integer id) {
        PreparedStatement statement = null;
        ResultSet set = null;

        try {
            statement = conn.prepareStatement("SELECT * FROM department WHERE department.Id = ?");
            statement.setInt(1, id);
            set = statement.executeQuery();

            if (set.next()) {
                return instantiateDepartment(set);
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(statement);
            DB.closeResultSet(set);
        }
    }

    private Department instantiateDepartment(ResultSet set) throws SQLException {
        Department department = new Department();
        department.setId(set.getInt("Id"));
        department.setName(set.getString("Name"));
        return department;
    }

    @Override
    public List<Department> findAll() {
        return null;
    }
}
