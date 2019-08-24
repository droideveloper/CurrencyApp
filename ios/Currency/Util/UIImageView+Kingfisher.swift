//
//  UIImageView+Kingfisher.swift
//  Currency
//
//  Created by Fatih Şen on 24.08.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation
import Kingfisher

extension UIImageView {
	
	public func loadCircularCrop(_ url: URL?) {
		let r = self.bounds.width / 2
		// if image is not square then we should say ops
		loadRoundedRectCrop(url, r)
	}
	
	// will load image with rounded rect and crop it center by default as image size
	public func loadRoundedRectCrop(_ url: URL?, _ r: CGFloat = defaultRoundRect, _ size: CGSize? = nil, _ pivot: CGPoint? = nil) {
		let selfSize = size ?? self.bounds.size
		let selfPivot = pivot ?? CGPoint(x: 0.5, y: 0.5)
		
		var options = defaultOptions
	
		let processors = CroppingImageProcessor(size: CGSize(width: selfSize.width, height: selfSize.height / 2), anchor: selfPivot)
			>> RoundCornerImageProcessor(cornerRadius: r)
		
		options.append(.processor(FlagImageProcessor(size: selfSize, anchor: selfPivot)))
		options.append(.processor(processors))
		// now do cal it
		load(url, options: options)
	}
	
	public func loadCrop(_ url: URL?) {
		var options = defaultOptions
		
		let processors = CroppingImageProcessor(size: self.bounds.size, anchor: CGPoint(x: 0.5, y: 0.5))
		
		options.append(.processor(processors))
		load(url, options: options)
	}
	
	// will load image circular
	public func loadCircularScale(_ url: URL?) {
		let r = self.bounds.width / 2
		// if image is not square then we should say ops
		loadRoundedRectScale(url, r)
	}
	
	// will load image rectanguler by size
	public func loadRoundedRectScale(_ url: URL?, _ r: CGFloat = defaultRoundRect) {
		var options = defaultOptions
		
		let processors = DownsamplingImageProcessor(size: self.bounds.size)
			>> RoundCornerImageProcessor(cornerRadius: r)
		
		options.append(.processor(processors))
		// now do call it
		load(url, options: options)
	}
	
	public func loadScale(_ url: URL?) {
		var options = defaultOptions
		
		let processors = DownsamplingImageProcessor(size: self.bounds.size)
		
		options.append(.processor(processors))
		load(url, options: options)
	}
	
	// base load func
	private func load(_ url: URL?,
										placeholder: String = defaultPlaceholder,
										errorPlaceholder: String = defaultErrorPlaceholder,
										options: KingfisherOptionsInfo = defaultOptions) {
		// load place holder
		let p = UIImage(named: placeholder)
		// error place holder
		let e = UIImage(named: errorPlaceholder)
		// load image now
		self.kf.setImage(with: url, placeholder: p, options: options) { response in
			switch response {
			case .success:
				// no opt
				break
			case .failure:
				// bind error image if there is one provided
				self.image = e
				break
			}
		}
	}
}

public let defaultOptions: KingfisherOptionsInfo = [
	.scaleFactor(UIScreen.main.scale),
	.transition(.none),
	.cacheOriginalImage
]

public let defaultPlaceholder = "placeholder"

public let defaultErrorPlaceholder = "errorPlaceholder"

public let defaultRoundRect: CGFloat = 10



public struct FlagImageProcessor: ImageProcessor {
	
	/// Identifier of the processor.
	/// - Note: See documentation of `ImageProcessor` protocol for more.
	public let identifier: String
	
	/// Target size of output image should be.
	public let size: CGSize
	
	/// Anchor point from which the output size should be calculate.
	/// The anchor point is consisted by two values between 0.0 and 1.0.
	/// It indicates a related point in current image.
	/// See `CroppingImageProcessor.init(size:anchor:)` for more.
	public let anchor: CGPoint
	
	/// Creates a `CroppingImageProcessor`.
	///
	/// - Parameters:
	///   - size: Target size of output image should be.
	///   - anchor: The anchor point from which the size should be calculated.
	///             Default is `CGPoint(x: 0.5, y: 0.5)`, which means the center of input image.
	/// - Note:
	///   The anchor point is consisted by two values between 0.0 and 1.0.
	///   It indicates a related point in current image, eg: (0.0, 0.0) for top-left
	///   corner, (0.5, 0.5) for center and (1.0, 1.0) for bottom-right corner.
	///   The `size` property of `CroppingImageProcessor` will be used along with
	///   `anchor` to calculate a target rectangle in the size of image.
	///
	///   The target size will be automatically calculated with a reasonable behavior.
	///   For example, when you have an image size of `CGSize(width: 100, height: 100)`,
	///   and a target size of `CGSize(width: 20, height: 20)`:
	///   - with a (0.0, 0.0) anchor (top-left), the crop rect will be `{0, 0, 20, 20}`;
	///   - with a (0.5, 0.5) anchor (center), it will be `{40, 40, 20, 20}`
	///   - while with a (1.0, 1.0) anchor (bottom-right), it will be `{80, 80, 20, 20}`
	public init(size: CGSize, anchor: CGPoint = CGPoint(x: 0.5, y: 0.5)) {
		self.size = size
		self.anchor = anchor
		self.identifier = "com.onevcat.Kingfisher.FlagImageProcessor(\(size)_\(anchor))"
	}
	
	/// Processes the input `ImageProcessItem` with this processor.
	///
	/// - Parameters:
	///   - item: Input item which will be processed by `self`.
	///   - options: Options when processing the item.
	/// - Returns: The processed image.
	///
	/// - Note: See documentation of `ImageProcessor` protocol for more.
	public func process(item: ImageProcessItem, options: KingfisherParsedOptionsInfo) -> Image? {
		let newSize = CGSize(width: size.width, height: size.height / 2)
		switch item {
		case .image(let image):
			return image
				.kf.crop(to: newSize, anchorOn: anchor)
				.kf.scaled(to: 3)
		case .data: return (DefaultImageProcessor.default >> self).process(item: item, options: options)
		}
	}
}
