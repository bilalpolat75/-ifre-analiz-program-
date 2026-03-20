import java.util.Scanner;
/*
Öğrenci numarası = 250706045
Ad = Bilal
Soyad = Polat
 */

public class Main {
    public static final String bıtıskelımesı = "BİTİR";
    public static void main(String[] args){
        Scanner klavye = new Scanner(System.in);
        System.out.println("------------------------");
        System.out.println("-SİFRE ANALİZ PROGRAMI-");
        System.out.println("------------------------");
        System.out.println("Kurallar");
        System.out.println("Şifrenizin uzunlugu 8 den az 20 den fazla olamaz");
        System.out.println("Şifre en az 1 büyük harf , 1 küçük harf , 1 rakam ,1 özel karakter bulunmalıdır ");
        System.out.println("Şifrenizin içinde boşluk olamaz");
        System.out.println("Aynı karakter 3 kez bulunamaz");
        //degisken tanımlamaları
        int toplamSifre = 0;
        int gecerliSifreSayisi = 0;
        int gecersizSifreSayisi = 0;
        int cokGucluSayisi = 0;
        double toplamGucPuani = 0;

        int enYuksekPuan = -1;
        String enYuksekPuanliSifre = "";

        int enFazlaRakamSayisi = -1;
        String enFazlaRakamliSifre = "";

        // özet icin hata sayacları
        int hataUzunlukSayac = 0;
        int hataTurSayac = 0;
        int hataBoslukSayac = 0;
        int hataArdisikSayac = 0;
        int hataYasakliSayac = 0;

        while (true) {
            System.out.print("Şifre giriniz: ");
            String sifre = klavye.nextLine();

            // Döngüden çıkış kontrolü
            if (sifre.equals(bıtıskelımesı)) {
                break;
            }

            toplamSifre++;
            int uzunluk = sifre.length();

            // birinci kosul sartımız
            String hataNedeni = "";
            boolean gecerli = true;

            // sifre uzunlugu kontrolu
            if (uzunluk < 8 || uzunluk > 20) {
                gecerli = false;
                hataNedeni = "UZUNLUK HATALI";
                hataUzunlukSayac++;
            }

            // karakter türü kontrolü
            boolean buyukHarfVar = false;
            boolean kucukHarfVar = false;
            boolean rakamVar = false;
            boolean ozelKarakterVar = false;
            boolean boslukVar = false;
            int rakamAdedi = 0;

            for (int i = 0; i < uzunluk; i++) {
                char c = sifre.charAt(i);
                if (c >= 'A' && c <= 'Z') buyukHarfVar = true;
                else if (c >= 'a' && c <= 'z') kucukHarfVar = true;
                else if (c >= '0' && c <= '9') {
                    rakamVar = true;
                    rakamAdedi++;
                } else if (c == ' ') boslukVar = true;
                else ozelKarakterVar = true; // Harf, rakam veya boşluk değilse özel karakterdir
            }

            if (gecerli && (!buyukHarfVar || !kucukHarfVar || !rakamVar || !ozelKarakterVar)) {
                gecerli = false;
                hataNedeni = "KARAKTER TÜRÜ EKSİK";
                hataTurSayac++;
            }

            //Boşluk Kontrolü
            if (gecerli && boslukVar) {
                gecerli = false;
                hataNedeni = "BOŞLUK İÇERİYOR";
                hataBoslukSayac++;
            }

            //Ardışık Tekrar Kontrolü
            if (gecerli) {
                boolean ardisikVar = false;
                for (int i = 0; i < uzunluk - 2; i++) {
                    if (sifre.charAt(i) == sifre.charAt(i + 1) && sifre.charAt(i + 1) == sifre.charAt(i + 2)) {
                        ardisikVar = true;
                        break;
                    }
                }
                if (ardisikVar) {
                    gecerli = false;
                    hataNedeni = "AYNI KARAKTER 3 KEZ ARD ARDA KULLANILMIŞ";
                    hataArdisikSayac++;
                }
            }

            // Yasaklı içerik kontrolü String kücük harfe ceviriliyor
            if (gecerli) {
                String kucukSifre = sifre.toLowerCase();
                boolean yasakliVar = false;

                // Substring ile yasaklı kelime taraması
                for (int i = 0; i <= kucukSifre.length() - 4; i++) {
                    if (i <= kucukSifre.length() - 5 && kucukSifre.substring(i, i + 5).equals("admin")) yasakliVar = true;
                    if (i <= kucukSifre.length() - 4 && kucukSifre.substring(i, i + 4).equals("1234")) yasakliVar = true;
                    if (i <= kucukSifre.length() - 6 && kucukSifre.substring(i, i + 6).equals("qwerty")) yasakliVar = true;
                    if (i <= kucukSifre.length() - 8 && kucukSifre.substring(i, i + 8).equals("password")) yasakliVar = true;
                }

                if (yasakliVar) {
                    gecerli = false;
                    hataNedeni = "YASAKLI İFADE İÇERİYOR";
                    hataYasakliSayac++;
                }
            }

            // ÇIKTI ÜRETME
            if (!gecerli) {
                gecersizSifreSayisi++;
                System.out.println("GEÇERSİZ ŞİFRE -> " + hataNedeni);
            } else {
                gecerliSifreSayisi++;

                // Şifre güc puanı hesaplama
                int uzunlukPuani = 0;
                if (uzunluk >= 8 && uzunluk <= 10) uzunlukPuani = 10;
                else if (uzunluk >= 11 && uzunluk <= 14) uzunlukPuani = 20;
                else if (uzunluk >= 15 && uzunluk <= 20) uzunlukPuani = 30;

                int turPuani = 0;
                if (buyukHarfVar) turPuani += 10;
                if (kucukHarfVar) turPuani += 10;
                if (rakamVar) turPuani += 10;
                if (ozelKarakterVar) turPuani += 15;

                //farklı karakter sayısını bulma
                int farkliKarakterSayisi = 0;
                for (int i = 0; i < uzunluk; i++) {
                    boolean dahaOnceGectiMi = false;
                    for (int j = 0; j < i; j++) {
                        if (sifre.charAt(i) == sifre.charAt(j)) {
                            dahaOnceGectiMi = true;
                            break;
                        }
                    }
                    if (!dahaOnceGectiMi) {
                        farkliKarakterSayisi++;
                    }
                }

                int cesitlilikPuani = 0;
                if (farkliKarakterSayisi >= 5 && farkliKarakterSayisi <= 7) cesitlilikPuani = 5;
                else if (farkliKarakterSayisi >= 8 && farkliKarakterSayisi <= 10) cesitlilikPuani = 10;
                else if (farkliKarakterSayisi >= 11) cesitlilikPuani = 20;

                // Tekrar Cezası
                int tekrarCezasi = (uzunluk - farkliKarakterSayisi) * 1;

                int gucPuani = uzunlukPuani + turPuani + cesitlilikPuani - tekrarCezasi;
                toplamGucPuani += gucPuani;

                // Güç satır
                String gucSinifi = "";
                if (gucPuani >= 0 && gucPuani <= 34) gucSinifi = "ZAYIF";
                else if (gucPuani >= 35 && gucPuani <= 59) gucSinifi = "ORTA";
                else if (gucPuani >= 60 && gucPuani <= 79) gucSinifi = "GÜÇLÜ";
                else if (gucPuani >= 80) {
                    gucSinifi = "ÇOK GÜÇLÜ";
                    cokGucluSayisi++;
                }

                // şifre dönüştürme
                String maskelenmisSifre = "";
                for (int i = 0; i < uzunluk; i++) {
                    char c = sifre.charAt(i);
                    int karakterTipi; // 1: Büyük, 2: Küçük, 3: Rakam, 4: Özel

                    if (c >= 'A' && c <= 'Z') karakterTipi = 1;
                    else if (c >= 'a' && c <= 'z') karakterTipi = 2;
                    else if (c >= '0' && c <= '9') karakterTipi = 3;
                    else karakterTipi = 4;

                    switch (karakterTipi) {
                        case 1:
                            maskelenmisSifre += c;
                            break;
                        case 2:
                            maskelenmisSifre += "*";
                            break;
                        case 3:
                            maskelenmisSifre += "#";
                            break;
                        case 4:
                            maskelenmisSifre += c;
                            break;
                    }
                }

                // özet rapor güncellemesi
                if (gucPuani > enYuksekPuan) {
                    enYuksekPuan = gucPuani;
                    enYuksekPuanliSifre = sifre;
                }

                if (rakamAdedi > enFazlaRakamSayisi) {
                    enFazlaRakamSayisi = rakamAdedi;
                    enFazlaRakamliSifre = sifre;
                }

                // Ekrana Yazdırma
                System.out.println("GEÇERLİ ŞİFRE");
                System.out.println("Şifre: " + sifre);
                System.out.println("Uzunluk: " + uzunluk);
                System.out.println("Farklı karakter sayısı: " + farkliKarakterSayisi);
                System.out.println("Güç puanı: " + gucPuani);
                System.out.println("Güç sınıfı: " + gucSinifi);
                System.out.println("Maskelenmiş şifre: " + maskelenmisSifre);
            }
        } // While sonu

        // program sonu özeti
        System.out.println("===== ÖZET RAPOR =====");
        System.out.println("Toplam girilen şifre sayısı: " + toplamSifre);
        System.out.println("Geçerli şifre sayısı: " + gecerliSifreSayisi);
        System.out.println("Geçersiz şifre sayısı: " + gecersizSifreSayisi);

        if (gecerliSifreSayisi > 0) {
            System.out.println("En yüksek puanlı şifre: " + enYuksekPuanliSifre);
            System.out.println("Ortalama güç puanı: " + (toplamGucPuani / gecerliSifreSayisi));
            System.out.println("ÇOK GÜÇLÜ sınıfındaki şifre sayısı: " + cokGucluSayisi);
            System.out.println("İçinde en fazla rakam bulunan geçerli şifre: " + enFazlaRakamliSifre);
        }

        if (gecersizSifreSayisi > 0) {
            int maxHata = hataUzunlukSayac;
            String enSikGorulenHata = "UZUNLUK HATALI";

            if (hataTurSayac > maxHata) {
                maxHata = hataTurSayac;
                enSikGorulenHata = "KARAKTER TÜRÜ EKSİK";
            }
            if (hataBoslukSayac > maxHata) {
                maxHata = hataBoslukSayac;
                enSikGorulenHata = "BOŞLUK İÇERİYOR";
            }
            if (hataArdisikSayac > maxHata) {
                maxHata = hataArdisikSayac;
                enSikGorulenHata = "AYNI KARAKTER 3 KEZ ARD ARDA KULLANILMIŞ";
            }
            if (hataYasakliSayac > maxHata) {
                maxHata = hataYasakliSayac;
                enSikGorulenHata = "YASAKLI İFADE İÇERİYOR";
            }

            System.out.println("En sık görülen geçersizlik nedeni: " + enSikGorulenHata);
        }

        klavye.close();



    }
}