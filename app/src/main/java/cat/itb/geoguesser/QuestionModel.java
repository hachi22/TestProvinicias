package cat.itb.geoguesser;

public class QuestionModel {
    int pregunta;
    String respuestaCorrecta;
    String respuestaMala1;
    String respuestaMala2;
    String respuestaMala3;

    public QuestionModel(int pregunta, String respuestaCorrecta, String respuestaMala1, String respuestaMala2, String respuestaMala3) {
        this.pregunta = pregunta;
        this.respuestaCorrecta = respuestaCorrecta;
        this.respuestaMala1 = respuestaMala1;
        this.respuestaMala2 = respuestaMala2;
        this.respuestaMala3 = respuestaMala3;
    }

    public int getPregunta() {
        return pregunta;
    }

    public void setPregunta(int pregunta) {
        this.pregunta = pregunta;
    }

    public String getRespuestaCorrecta() {
        return respuestaCorrecta;
    }

    public void setRespuestaCorrecta(String respuestaCorrecta) {
        this.respuestaCorrecta = respuestaCorrecta;
    }

    public String getRespuestaMala1() {
        return respuestaMala1;
    }

    public void setRespuestaMala1(String respuestaMala1) {
        this.respuestaMala1 = respuestaMala1;
    }

    public String getRespuestaMala2() {
        return respuestaMala2;
    }

    public void setRespuestaMala2(String respuestaMala2) {
        this.respuestaMala2 = respuestaMala2;
    }

    public String getRespuestaMala3() {
        return respuestaMala3;
    }

    public void setRespuestaMala3(String respuestaMala3) {
        this.respuestaMala3 = respuestaMala3;
    }

}

