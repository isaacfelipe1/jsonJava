import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Scanner;
import org.json.JSONObject;
public class App {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite um CEP (somente números): ");
        String cep = scanner.nextLine();
        // Construir a URL com o CEP digitado
        String url = "https://viacep.com.br/ws/" + cep + "/json";
        URI endereco = URI.create(url);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String responseBody = response.body();
        //Utilizando a Biblioteca Json-Java
        JSONObject json = new JSONObject(responseBody);
        
        String cidadeEstado = json.getString("localidade") + "/" + json.getString("uf");
        String nomeRuaAvenida = json.getString("logradouro");
        String complemento = json.optString("complemento", "");
        String bairro = json.getString("bairro");
        
        System.out.println("Cidade/Estado: " + cidadeEstado);
        System.out.println("Nome da Rua/Avenida: " + nomeRuaAvenida);
        
        if (!complemento.isEmpty()) {
            System.out.println("Complemento: " + complemento);
        }
        
        System.out.println("Bairro: " + bairro);

        scanner.close();
    }
}

