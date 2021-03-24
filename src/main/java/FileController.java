import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import org.apache.velocity.tools.generic.DateTool;
import spark.Request;
import spark.Response;
import spark.Route;
import utils.Database;
import utils.Views;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileController {
    public static Route serverFileUpload = (Request request, Response response) -> {
        response.type("application/json");

            Path tempFile = Files.createTempFile(Paths.get("C:\\Users\\matth\\IdeaProjects\\allinsureapp\\uploads"), "", "");

        request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
        String filename = getFileName(request.raw().getPart("file"));
            try (InputStream input = request.raw().getPart("file").getInputStream()) { // getPart needs to use same "name" as input field in form
                Files.copy(input, tempFile, StandardCopyOption.REPLACE_EXISTING);
            }

            ConnectionSource connectionSource = Database.databaseConn();
            Dao<SupportingDocument,Integer> supportingDocumentDao = DaoManager.createDao(connectionSource, SupportingDocument.class);
            SupportingDocument supportingDocument = new SupportingDocument();
            supportingDocument.setDocumentname(filename);
            supportingDocument.setStoredfilename(tempFile.getFileName().toString());
            supportingDocumentDao.create(supportingDocument);
        return "{ \"success\":true,\"file\":\"" + filename + "\", \"id\": " + supportingDocument.getDocumentid() + "}";
    };

    private static String getFileName(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                return cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

    public static Route serverFileDownload= (Request request, Response response) -> {
        int fileid = Integer.parseInt(request.params(":fileid"));
        ConnectionSource connectionSource = Database.databaseConn();
        Dao<SupportingDocument,Integer> supportingDocumentDao = DaoManager.createDao(connectionSource, SupportingDocument.class);
        SupportingDocument supportingDocument = supportingDocumentDao.queryBuilder().where().eq("documentid", fileid).queryForFirst();

        Path path = Paths.get("C:\\Users\\matth\\IdeaProjects\\allinsureapp\\uploads\\"+supportingDocument.getStoredfilename());
        byte[] data = null;
        try {
            data = Files.readAllBytes(path);
        } catch (Exception e1) {

            e1.printStackTrace();
        }

        HttpServletResponse raw = response.raw();
        response.header("Content-Disposition", "attachment; filename=" + supportingDocument.getDocumentname());
        response.type("application/force-download");
        try {
            raw.getOutputStream().write(data);
            raw.getOutputStream().flush();
            raw.getOutputStream().close();
        } catch (Exception e) {

            e.printStackTrace();
        }
        return raw;
    };
}
