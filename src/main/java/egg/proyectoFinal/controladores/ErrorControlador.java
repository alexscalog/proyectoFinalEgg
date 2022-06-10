package egg.proyectoFinal.controladores;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import static javax.servlet.RequestDispatcher.ERROR_STATUS_CODE;

public class ErrorControlador implements ErrorController {

    @RequestMapping("/error")
    public ModelAndView handleError(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("error");
        Integer status = (int) request.getAttribute(ERROR_STATUS_CODE);
        String message;

        switch (status) {
            case 403:
                message = "¡No tienes permiso para acceder a la dirección solicitada!";
                break;
            case 404:
                message = "¡La dirección solicitada no fue encontrada!";
                break;
            case 500:
                message = "Error interno del server.";
                break;
            default:
                message = "Error inesperado.";
        }

        mav.addObject("message", message);
        mav.addObject("status", status);
        return mav;
    }
}
