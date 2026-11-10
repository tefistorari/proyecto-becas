package com.UTN_BECAS.Sistema_Becas.Convocatorias.Service;

import com.UTN_BECAS.Sistema_Becas.Convocatorias.DTO.ReporteConvocatoriaResponse;
import com.UTN_BECAS.Sistema_Becas.Core.Exception.ReglaDeNegocioException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.awt.Color;

@Component
public class ReportePdfGenerator {
    private static final Font TITULO = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
    private static final Font ENCABEZADO = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);
    private static final Font TEXTO = FontFactory.getFont(FontFactory.HELVETICA, 10);

    public byte[] generar(ReporteConvocatoriaResponse r) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document doc = new Document(PageSize.A4, 40, 40, 40, 40);
            PdfWriter.getInstance(doc, out);
            doc.open();

            doc.add(new Paragraph("Informe de convocatoria", TITULO));
            doc.add(new Paragraph(r.nombreBeca() + " - " + r.anio(), TEXTO));
            doc.add(new Paragraph("Generado el " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    TEXTO));
            doc.add(new Paragraph(" "));
            doc.add(new Paragraph("Total de postulados: " + r.total(), TEXTO));
            doc.add(new Paragraph("Aceptados: " + r.aprobados(), TEXTO));
            doc.add(new Paragraph("Rechazados: " + r.rechazados(), TEXTO));
            doc.add(new Paragraph(" "));

            PdfPTable tabla = new PdfPTable(new float[] { 3, 3, 2, 2 });
            tabla.setWidthPercentage(100);
            tabla.setHeaderRows(1);

            for (String h : new String[] { "Apellido", "Nombre", "DNI", "Estado" }) {
                PdfPCell celda = new PdfPCell(new Phrase(h, ENCABEZADO));
                celda.setPadding(5);
                celda.setBackgroundColor(new Color(230, 234, 240));
                tabla.addCell(celda);
            }

            for (ReporteConvocatoriaResponse.Postulado p : r.postulados()) {
                String estado = p.estado().name();
                tabla.addCell(celda(p.apellido()));
                tabla.addCell(celda(p.nombre()));
                tabla.addCell(celda(p.dni()));
                tabla.addCell(celda(estado.charAt(0) + estado.substring(1).toLowerCase()));
            }

            doc.add(tabla);
            doc.close();
            return out.toByteArray();
        } catch (IOException | DocumentException e) {
            throw new ReglaDeNegocioException("No se pudo generar el informe: " + e.getMessage());
        }
    }

    private PdfPCell celda(String texto) {
        PdfPCell c = new PdfPCell(new Phrase(texto, TEXTO));
        c.setPadding(5);
        return c;
    }
}
