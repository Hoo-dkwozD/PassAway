// package sg.edu.sportsschool.Entities;

// import java.util.Set;

// import javax.persistence.Column;
// import javax.persistence.Entity;
// import javax.persistence.FetchType;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.Lob;
// import javax.persistence.OneToMany;
// import javax.persistence.Table;

// @Entity
// @Table(name = "barcode")
// public class Barcode {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Integer barcodeId;

//     @Lob
//     @Column(name = "image", columnDefinition = "BLOB")
//     private byte[] image;

//     @OneToMany(mappedBy = "barcode", fetch = FetchType.LAZY)
//     private Set<Pass> passes;

//     public Barcode() {
//     }

//     public Barcode(byte[] image) {
//       this.image = image;
//     }

//     public Integer getBarcodeId() {
//         return barcodeId;
//     }

//     public byte[] getImage() {
//         return image;
//     }

//     public void setImage(byte[] image) {
//         this.image = image;
//     }
// }
