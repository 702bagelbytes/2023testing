#[cfg(test)]
mod test {
    use crate::clamp;

    #[test]
    fn clamp_up() {
        let r = clamp(1, 5..10, 0..100);
        assert_eq!(r, 0f64);
    }

    #[test]
    fn clamp_down() {
        let r = clamp(101, 0..100, 5..6);
        assert_eq!(r, 6f64);
    }

    #[test]
    fn halfway() {
        let r = clamp(50, 0..100, 5..6);
        assert_eq!(r, 5.5f64);
    }

    #[test]
    fn four_fifths() {
        let r = clamp(12, 0..15, 20..30);
        assert_eq!(r, 28f64);
    }
}