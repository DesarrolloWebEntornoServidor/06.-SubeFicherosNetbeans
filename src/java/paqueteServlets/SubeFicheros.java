package paqueteServlets; 

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/SubeFicheros")

// @MultipartConfig (
//      location = "carpeta donde se guardan los archivos (creada previamente)", 
//      maxFileSize =  tamaño maximo de archivo a subir (entero largo)
// )

@MultipartConfig(location = "c:\\upload", maxFileSize = 10485760L) // (Barra para escapar c:\\ , 10MB)

public class SubeFicheros extends HttpServlet {
	private static final long serialVersionUID = 1L;

    // Los ficheros se envían usando el método post y van en el cuerpo de la petición
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String descripcion = request.getParameter("descripcion"); // Recibe la descripción del archivo
       
        Collection <Part> parteArchivos =request.getParts(); // Recibe los archivos en una collección de objetos Part 
        int numArchivos=parteArchivos.size()-1;
    
        Iterator<Part> it = parteArchivos.iterator();
        Part parteArchivo;
        String nombreArchivo;
        long tamanio;
        String tipo;
        
        // Creación del html de salida
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SubeFichero</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Operación realizada con éxito</h1>");
            out.println("<table border=1px>");
            out.println("<tr><td>Número de archivos subidos</td><td>" + numArchivos + "</td></tr>");
            out.println("<tr><td>Descripción de los archivos</td><td>" + descripcion + "</td></tr>");
            out.println("</table> <br />");
            out.println("<table border=1px>");
            out.println("<tr><td>Archivo subido</td><td>Tamaño</td> <td>Tipo</td></tr>");
            it.next(); // Salta una parte al principio que no es un fichero
            while (it.hasNext()){
                parteArchivo=it.next();
                nombreArchivo = parteArchivo.getSubmittedFileName(); // Extrae el nombre original del archivo del objeto Part
                parteArchivo.write(nombreArchivo); // Guarda en el disco el archivo con el nombre original  
                tamanio=parteArchivo.getSize();
                tipo=parteArchivo.getContentType();                  
                out.println("<tr><td><b>C:\\upload\\" + nombreArchivo + "</td><td>" + tamanio + " bytes</td><td>"
                        + tipo + "</td></tr>");
            }
            out.println("</table>");
            out.println("</body>");
            out.println("</html>");
            out.close();
        }
    }
}