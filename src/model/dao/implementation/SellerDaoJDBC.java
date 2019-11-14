package model.dao.implementation;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SellerDaoJDBC implements SellerDao {

    private Connection conn;

    public SellerDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Seller obj) {

    }

    @Override
    public void update(Seller obj) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement statement = null;
        ResultSet set = null;

        try {
            statement = conn.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                            + "FROM seller INNER JOIN department "
                            + "ON seller.DepartmentId = department.Id "
                            + "WHERE seller.Id = ?"
            );
            statement.setInt(1, id);
            set = statement.executeQuery();
            if (set.next()) {
                Department department = instantiateDepartment(set);
                return instantiateSeller(set, department);
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
        department.setId(set.getInt("DepartmentId"));
        department.setName(set.getString("DepName"));
        return department;
    }

    private Seller instantiateSeller(ResultSet set, Department department) throws SQLException {
        Seller obj = new Seller();
        obj.setId(set.getInt("Id"));
        obj.setName(set.getString("Name"));
        obj.setEmail(set.getString("Email"));
        obj.setBaseSalary(set.getDouble("BaseSalary"));
        obj.setBirthDate(set.getDate("BirthDate"));
        obj.setDepartment(department);
        return obj;
    }

    @Override
    public List<Seller> findAll() {
        return null;
    }
}
