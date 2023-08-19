package ra.uploadvideo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.uploadvideo.model.Video;
import ra.uploadvideo.util.ConnectDB;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class VideoService implements IGenericService<Video,Integer> {
    @Autowired
    private DataSource dataSource;

    @Override
    public List<Video> findAll() {
        Connection conn = null;
        try {
            conn =dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        List<Video> videos = new ArrayList<>();
        try {
            CallableStatement callSt = conn.prepareCall("{call findAll}");
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                Video v = new Video();
                v.setId(rs.getInt("id"));
                v.setName(rs.getString("name"));
                v.setDescription(rs.getString("description"));
                v.setTitle(rs.getString("title"));
                videos.add(v);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
          if (conn!=null){
              try {
                  conn.close();
              } catch (SQLException e) {
                  throw new RuntimeException(e);
              }
          }
        }
        return videos;
    }


    @Override
    public void deleteById(Integer id) {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            CallableStatement callSt = conn.prepareCall("{call deleteById(?)}");
            callSt.setInt(1,id);
            callSt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


    @Override
    public void save(Video video) {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            if (video.getId() == 0) {
                // thêm moi
                CallableStatement callSt = conn.prepareCall("{call addVideo(?,?,?)}");
                callSt.setString(1, video.getName());
                callSt.setString(2, video.getDescription());
                callSt.setString(3, video.getTitle());
                callSt.executeUpdate();

            } else {
                // cap nhat
                CallableStatement callSt = conn.prepareCall("{call updateVideo(?,?,?,?)}");
                callSt.setInt(1, video.getId());
                callSt.setString(2, video.getName());
                callSt.setString(3, video.getDescription());
                callSt.setString(4, video.getTitle());
                callSt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
           if (conn!=null){
               try {
                   conn.close();
               } catch (SQLException e) {
                   throw new RuntimeException(e);
               }
           }
        }

    }



    @Override
    public Video findById(Integer id) {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Video v = null;
        try {
            CallableStatement callSt = conn.prepareCall("{call findById(?)}");
            callSt.setInt(1,id);
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                v = new Video();
                v.setId(rs.getInt("id"));
                v.setName(rs.getString("name"));
                v.setDescription(rs.getString("description"));
                v.setTitle(rs.getString("title"));
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
          if (conn!=null){
              try {
                  conn.close();
              } catch (SQLException e) {
                  throw new RuntimeException(e);
              }
          }
        }
        return v;
    }
}
