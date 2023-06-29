package com.pharmanuman.services;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.pharmanuman.dao.PlaceOrderRepository;
import com.pharmanuman.dao.UserRepository;
import com.pharmanuman.entities.PlaceOrder;
import com.pharmanuman.entities.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class PdfService {

	@Autowired
	private PlaceOrderRepository placeOrderRepository;

	@Autowired
	private UserRepository userRepository;

	private Logger logger = LoggerFactory.getLogger(PdfService.class);

	public ByteArrayInputStream createPdf(Principal p) {
		String name = p.getName();
		System.out.println("k ho to name " + name);

		User b = this.userRepository.getUserByUserName(name);

		List<PlaceOrder> orders = b.getPlaceOrders();

		String pharmacyName = null;
		for (PlaceOrder order : orders) {
			pharmacyName = order.getPharmacyName();
			// Do something with the pharmacyName
		}

		List<PlaceOrder> a = this.placeOrderRepository.findPlaceOrderByPharmacyName(pharmacyName);

		// Filter orders by status "done"
		List<PlaceOrder> doneOrders = new ArrayList<>();
		for (PlaceOrder order : a) {
			if (order.getStatus().equalsIgnoreCase("done")) {
				doneOrders.add(order);
			}
		}

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		Document document = new Document(PageSize.A4.rotate());

		try {
			PdfWriter writer = PdfWriter.getInstance(document, outputStream);
			document.open();

			PdfPTable table = new PdfPTable(9);
			table.setWidthPercentage(100);
			table.setSpacingBefore(10f);
			table.setSpacingAfter(10f);

			float[] columnWidths = { 0.9f, 1f, 2f, 0.8f, 1.f, 1.8f, 2f, 2f, 1.5f };
			table.setWidths(columnWidths);

			addTableHeader(table);

			int orderNo = 1;
			float totalAmount = 0.0f; // Initialize total amount

			for (PlaceOrder order : doneOrders) {
				addTableRow(table, order, orderNo++);

				// Calculate total amount
				totalAmount += order.getPrice() * order.getQuantity();
			}

			// Add a row for the total amount
			PdfPCell totalAmountCell = createCell("Total Amount:", Element.ALIGN_RIGHT);
			totalAmountCell.setColspan(4); // Span multiple columns
			table.addCell(totalAmountCell);

			PdfPCell totalValueCell = createCell("Rs. " + totalAmount, Element.ALIGN_RIGHT);
			totalValueCell.setColspan(5); // Span multiple columns
			table.addCell(totalValueCell);

			document.add(table);
			document.close();
		} catch (Exception e) {
			logger.error("Error generating PDF", e);
		}

		return new ByteArrayInputStream(outputStream.toByteArray());
	}

	private void addTableHeader(PdfPTable table) {
		String[] headerText = { "Order No.", "Id", "Name", "Quantity", "Price", "Total", "Created date", "Arrive date",
				"Stockist Name" };

		for (String column : headerText) {
			PdfPCell headerCell = new PdfPCell();
			headerCell.setPhrase(new Phrase(column));
			headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			headerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			headerCell.setPadding(5f);
			headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			table.addCell(headerCell);
		}
	}

	private void addTableRow(PdfPTable table, PlaceOrder order, int orderNo) {
		table.addCell(createCell(String.valueOf(orderNo), Element.ALIGN_CENTER));
		table.addCell(createCell(String.valueOf(order.getPoid()), Element.ALIGN_CENTER));
		table.addCell(createCell(order.getName(), Element.ALIGN_LEFT));
		table.addCell(createCell(String.valueOf(order.getQuantity()), Element.ALIGN_CENTER));
		table.addCell(createCell("Rs. " + order.getPrice(), Element.ALIGN_RIGHT));
		table.addCell(createCell("Rs. " + (order.getPrice() * order.getQuantity()), Element.ALIGN_RIGHT));
		table.addCell(createCell(order.getCreatedDate().toString(), Element.ALIGN_CENTER));
		table.addCell(createCell(order.getArriveDate().toString(), Element.ALIGN_CENTER));
		table.addCell(createCell(order.getStockist(), Element.ALIGN_LEFT));
	}

	private PdfPCell createCell(String content, int alignment) {
		PdfPCell cell = new PdfPCell(new Phrase(content));
		cell.setHorizontalAlignment(alignment);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPadding(5f);
		return cell;
	}

}
