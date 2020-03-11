package client;

public class main {
    public static void main(String[] args) {
        HttpRequests requestGet = new HttpRequests("https://www.google.com.br");
        requestGet.sendGet();
    }
}
