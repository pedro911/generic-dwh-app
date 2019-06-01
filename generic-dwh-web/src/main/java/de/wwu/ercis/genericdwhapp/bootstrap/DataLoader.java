package de.wwu.ercis.genericdwhapp.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {


    @Override
    public void run(String... args) throws Exception {
        System.out.println("################");
        System.out.println("Run test........");
        System.out.println("################");
    }

    private void loadData() {
        //implement
    }
}
