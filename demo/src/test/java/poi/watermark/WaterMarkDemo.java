//import java.io.*;
//
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.xssf.usermodel.*;
//
//import org.apache.poi.util.IOUtils;
//import org.apache.poi.ss.util.ImageUtils;
//
//import org.apache.poi.openxml4j.opc.*;
//import org.apache.poi.POIXMLDocumentPart;
//
//import org.apache.xmlbeans.XmlObject;
//
//import static org.apache.poi.POIXMLTypeLoader.DEFAULT_XML_OPTIONS;
//
//public class WaterMarkDemo {
//
// static void createPictureForHeader(XSSFSheet sheet, int pictureIdx, String pictureTitle, int vmlIdx, String headerPos) throws Exception {
//  OPCPackage opcpackage = sheet.getWorkbook().getPackage();
//
//  //creating /xl/drawings/vmlDrawing1.vml
//  PackagePartName partname = PackagingURIHelper.createPartName("/xl/drawings/vmlDrawing" + vmlIdx+ ".vml");
//  PackagePart part = opcpackage.createPart(partname, "application/vnd.openxmlformats-officedocument.vmlDrawing");
//  //creating new VmlDrawing
//  VmlDrawing vmldrawing = new VmlDrawing(part);
//
//  //creating the relation to the picture in /xl/drawings/_rels/vmlDrawing1.vml.rels
//  XSSFPictureData picData = sheet.getWorkbook().getAllPictures().get(pictureIdx);
//  String rIdPic = vmldrawing.addRelation(null, XSSFRelation.IMAGES, picData).getRelationship().getId();
//
//  //get image dimension
//  ByteArrayInputStream is = new ByteArrayInputStream(picData.getData());
//  java.awt.Dimension imageDimension = ImageUtils.getImageDimension(is, picData.getPictureType());
//  is.close();
//
//  //updating the VmlDrawing
//  vmldrawing.setRIdPic(rIdPic);
//  vmldrawing.setPictureTitle(pictureTitle);
//  vmldrawing.setImageDimension(imageDimension);
//  vmldrawing.setHeaderPos(headerPos);
//
//  //creating the relation to /xl/drawings/vmlDrawing1.xml in /xl/worksheets/_rels/sheet1.xml.rels
//  String rIdExtLink = sheet.addRelation(null, XSSFRelation.VML_DRAWINGS, vmldrawing).getRelationship().getId();
//
//  //creating the <legacyDrawingHF r:id="..."/> in /xl/worksheets/sheetN.xml
//  sheet.getCTWorksheet().addNewLegacyDrawingHF().setId(rIdExtLink);
//
// }
//
// public static void main(String[] args) throws Exception {
//
//  Workbook workbook = new XSSFWorkbook();
//
//  Sheet sheet;
//  Header header;
//  InputStream is;
//  byte[] bytes;
//
//  int pictureIdx; //we need it later
//
//  sheet = workbook.createSheet();
//
//  header = sheet.getHeader();
//  header.setCenter("&G"); // &G means Graphic
//
//  //add picture data to this workbook
//  is = new FileInputStream("AF101880439_en-us_draft.png");
//  bytes = IOUtils.toByteArray(is);
//  pictureIdx = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
//  is.close();
//
//  //create header picture from picture data of this workbook
//  createPictureForHeader((XSSFSheet)sheet, pictureIdx, "AF101880439_en-us_draft", 1, "CH"/*CenterHeader*/);
//
//  workbook.write(new FileOutputStream("CreateExcelPictureInHeaderAKAWatermark.xlsx"));
//  workbook.close();
//
// }
//
// //class for VmlDrawing
// static class VmlDrawing extends POIXMLDocumentPart {
//
//  String rIdPic = "";
//  String pictureTitle = "";
//  java.awt.Dimension imageDimension = null;
//  String headerPos = "";
//
//  VmlDrawing(PackagePart part) {
//   super(part);
//  }
//
//  void setRIdPic(String rIdPic) {
//   this.rIdPic = rIdPic;
//  }
//
//  void setPictureTitle(String pictureTitle) {
//   this.pictureTitle = pictureTitle;
//  }
//
//  void setHeaderPos(String headerPos) {
//   this.headerPos = headerPos;
//  }
//
//  void setImageDimension(java.awt.Dimension imageDimension) {
//   this.imageDimension = imageDimension;
//  }
//
//  @Override
//  protected void commit() throws IOException {
//   PackagePart part = getPackagePart();
//   OutputStream out = part.getOutputStream();
//   try {
//    XmlObject doc = XmlObject.Factory.parse(
//
//      "<xml xmlns:v=\"urn:schemas-microsoft-com:vml\""
//     +" xmlns:o=\"urn:schemas-microsoft-com:office:office\""
//     +" xmlns:x=\"urn:schemas-microsoft-com:office:excel\">"
//     +" <o:shapelayout v:ext=\"edit\">"
//     +"  <o:idmap v:ext=\"edit\" data=\"1\"/>"
//     +" </o:shapelayout><v:shapetype id=\"_x0000_t75\" coordsize=\"21600,21600\" o:spt=\"75\""
//     +"  o:preferrelative=\"t\" path=\"m@4@5l@4@11@9@11@9@5xe\" filled=\"f\" stroked=\"f\">"
//     +"  <v:stroke joinstyle=\"miter\"/>"
//     +"  <v:formulas>"
//     +"   <v:f eqn=\"if lineDrawn pixelLineWidth 0\"/>"
//     +"   <v:f eqn=\"sum @0 1 0\"/>"
//     +"   <v:f eqn=\"sum 0 0 @1\"/>"
//     +"   <v:f eqn=\"prod @2 1 2\"/>"
//     +"   <v:f eqn=\"prod @3 21600 pixelWidth\"/>"
//     +"   <v:f eqn=\"prod @3 21600 pixelHeight\"/>"
//     +"   <v:f eqn=\"sum @0 0 1\"/>"
//     +"   <v:f eqn=\"prod @6 1 2\"/>"
//     +"   <v:f eqn=\"prod @7 21600 pixelWidth\"/>"
//     +"   <v:f eqn=\"sum @8 21600 0\"/>"
//     +"   <v:f eqn=\"prod @7 21600 pixelHeight\"/>"
//     +"   <v:f eqn=\"sum @10 21600 0\"/>"
//     +"  </v:formulas>"
//     +"  <v:path o:extrusionok=\"f\" gradientshapeok=\"t\" o:connecttype=\"rect\"/>"
//     +"  <o:lock v:ext=\"edit\" aspectratio=\"t\"/>"
//     +" </v:shapetype><v:shape id=\"" + headerPos + "\" o:spid=\"_x0000_s1025\" type=\"#_x0000_t75\""
//     +"  style='position:absolute;margin-left:0;margin-top:0;"
//     +"width:" + (int)imageDimension.getWidth() + "px;height:" + (int)imageDimension.getHeight() + "px;"
//     +"z-index:1'>"
//     +"  <v:imagedata o:relid=\""+ rIdPic + "\" o:title=\"" + pictureTitle + "\"/>"
//     +"  <o:lock v:ext=\"edit\" rotation=\"t\"/>"
//     +" </v:shape></xml>"
//
//    );
//    doc.save(out, DEFAULT_XML_OPTIONS);
//    out.close();
//   } catch (Exception ex) {
//    ex.printStackTrace();
//   }
//  }
//
// }
//
//}