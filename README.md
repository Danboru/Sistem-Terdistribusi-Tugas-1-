### Sistem Terdistribusi (Tugas 1)

# Persiapkan Server
- Hal yang pertama harus di lakukan adalah menjalankan server
- Pastikan mengisi socket yang akan di gunakan
- Di bagian server tidak di lakukan pengisian IP Adress, hal itu di karenakan Client yang membutuhkan ip adress untuk terhubung dengan Server
- Lihat Contoh di bawah ini

<img src="https://github.com/Danboru/Sistem-Terdistribusi-Tugas-1-/blob/master/Images/1.png"  width="220" height="400" />

# Persiapkan Client
- Persiapan Client
- Pastikan mengisi socket yang akan di gunakan
- Di bagian client di butuhkan pengisian IP Adress, isikan dengan ip dimana server di jalankan (Perikas Ip yang di gunakan menggunakan ifconfig)
- Lihat Contoh di bawah ini

<img src="https://github.com/Danboru/Sistem-Terdistribusi-Tugas-1-/blob/master/Images/2.png?raw=true"  width="220" height="400" />

# Sedikit Pembahasan
## Client side

- Membuka Koneksi Ke Server
           
          Socket toServer;
          
          try
            ipAdress = JOptionPane.showInputDialog("Masukkan Ip");
            toServer = new Socket(ipAdress, Integer.parseInt(jTport.getText()));
           
            serverReader = new BufferedReader(new InputStreamReader(toServer.getInputStream()));
            serverWriter = new BufferedWriter(new OutputStreamWriter(toServer.getOutputStream()));
            
            onConnect();
            
        } catch (Exception e) {
            System.out.println("Exception " + e);
        }

- Pastika Socket yang di gunakan sama

## Server Side

- Membuka Koneksi Untuk Client
           
          int socketNumber = 123;
          
          try {
            try {
                try {
                    
                    //Socket
                    ServerSocket serverSocket = new ServerSocket(socketNumber);

                } catch (Exception e) {
                    System.out.println("Could not listen");
                    System.exit(-1);
                }
                
                fromClient = serverSocket.accept();

            } catch (Exception e) {
                System.out.println("Accept Failed");
                System.exit(-1);
            }

            BufferedReader serverReader = new BufferedReader(new InputStreamReader(fromClient.getInputStream()));
            BufferedWriter serverWriter = new BufferedWriter(new OutputStreamWriter(fromClient.getOutputStream()));
                   

        } catch (Exception e) {
            System.out.println("Connection Failed")
            System.exit(-1);
        }

- Pastika Socket yang di gunakan sama

# Tahap Akhir
- Jika Sudah Maka aplikasi bisa di gunakan
- Lihat Contoh di bawah ini

<img src="https://github.com/Danboru/Sistem-Terdistribusi-Tugas-1-/blob/master/Images/3.png?raw=true"  width="400" height="400" />

# Demo App
- Buka Link Berikut untuk melihat demo
https://youtu.be/vfZDMh9-_QM
