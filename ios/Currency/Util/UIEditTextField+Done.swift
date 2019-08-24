//
//  UIEditTextField+Done.swift
//  Currency
//
//  Created by Fatih Şen on 24.08.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation
import UIKit

extension UITextField{
	
	@IBInspectable var doneOptions: Bool {
		get{
			return self.doneOptions
		}
		set (shouldHaveDoneOptions) {
			if shouldHaveDoneOptions {
				applyDoneOptions()
			}
		}
	}
	
	func applyDoneOptions() {
		let doneContainer: UIToolbar = UIToolbar(frame: CGRect.init(x: 0, y: 0, width: UIScreen.main.bounds.width, height: 50))
		doneContainer.barStyle = .default
		
		let flexSpace = UIBarButtonItem(barButtonSystemItem: .flexibleSpace, target: nil, action: nil)
		let done: UIBarButtonItem = UIBarButtonItem(title: "Done", style: .done, target: self, action: #selector(onDonePressed))
		
		let items = [flexSpace, done]
		doneContainer.items = items
		doneContainer.sizeToFit()
		
		self.inputAccessoryView = doneContainer
	}
	
	@objc func onDonePressed() {
		self.resignFirstResponder()
	}
}
