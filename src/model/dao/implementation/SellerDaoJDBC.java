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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public List<Seller> findByDepartment(Department department) {
        PreparedStatement statement = null;
        ResultSet set = null;

        try {
            statement = conn.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                            + "FROM seller INNER JOIN department "
                            + "ON seller.DepartmentId = Department.Id "
                            + "WHERE DepartmentId = ? "
                            + "ORDER BY Name"
            );
            statement.setInt(1, department.getId());
            set = statement.executeQuery();

            List<Seller> list = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            while (set.next()) {
                Department dep = map.get(set.getInt("DepartmentId"));

                if (dep == null) {
                    dep = instantiateDepartment(set);
                    map.put(set.getInt("DepartmentId"), dep);
                }
                Seller obj = instantiateSeller(set, dep);
                list.add(obj);
            }
            return list;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(statement);
            DB.closeResultSet(set);
        }
    }
}
