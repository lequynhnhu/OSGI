package osgi.hello.fr;


import osgi.hello.HelloService;

public class HelloServiceFr implements HelloService {

	public String sayHello(String name) {
                return "Bonjour " + name;
        }

}