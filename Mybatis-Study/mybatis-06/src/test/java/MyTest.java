import com.lk.Dao.TeacherMapper;
import com.lk.pojo.Teacher;
import com.lk.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;

public class MyTest {
    public static void main(String[] args) {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);

        Teacher teacher = mapper.getTeacher(1);
        System.out.println(teacher);

        sqlSession.close();
    }
}
